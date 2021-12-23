#include "p3180178-p3180049-p3180266-pizza2.h"

int rc;
/* ----------------------MUTEXES-------------------*/
/*this mutex should be locked when order 
enter the furnaces*/ 
pthread_mutex_t f_lock = PTHREAD_MUTEX_INITIALIZER;

/*this mutex should be locked when order 
enter the cooks*/
pthread_mutex_t ck_lock = PTHREAD_MUTEX_INITIALIZER;

/* this mutex should be lock when order
try to write in the console in order to avoid any colision */
pthread_mutex_t d_lock = PTHREAD_MUTEX_INITIALIZER;

/* this mutex should lock when the delivery guy is to take 
an order from a furnace*/
pthread_mutex_t c_lock = PTHREAD_MUTEX_INITIALIZER;

/* This mutex locks when we update the total prep time
variable which we'll use to find the average time of preperation */
pthread_mutex_t avg_lock = PTHREAD_MUTEX_INITIALIZER;

/* This mutex locks when we update the max prep time */
pthread_mutex_t max_lock = PTHREAD_MUTEX_INITIALIZER;

/* This mutex locks when we update the waiting time*/
pthread_mutex_t w8_lock = PTHREAD_MUTEX_INITIALIZER;

/* This mutex locks when we update the max waiting time*/
pthread_mutex_t max_w8_lock = PTHREAD_MUTEX_INITIALIZER;
/* ----------------------CONDITION VARIABLES-------------------*/
/* this condition variable is used when an
order is either trying to enter the furnace or 
leaving the furnace*/
pthread_cond_t f_cond = PTHREAD_COND_INITIALIZER;

/* this condition variable is used when a delivery guys
take an order from the furnaces*/
pthread_cond_t d_cond = PTHREAD_COND_INITIALIZER;

/* this condition variable is used when an
order is being prepared and by a cook*/
pthread_cond_t ck_cond = PTHREAD_COND_INITIALIZER;

/*Number of cooks, ovens and delivery guys available*/
int cooks = NCOOKS; 
int ovens = NOVEN;
int del = NDELIVER;

float avgtime = 0.f;
float max_time = -1.f;
float avgw8time = 0.f;
float maxw8time = -1.f;

struct Params{
    int noOfPizzas;
    int id;
};

int main (int argc, char* argv[]){
    /* Checking if the arguments where passed
    correctly from the user */
    if (argc <= 2 || argc > 3){
        printf("Invalid syntax please try this form\n");
        printf("||./a.out x y|| where x is the number of threads you would like and y is the root seed\n");
        exit(EXIT_FAILURE);
    }
    //------------------------------------------//
    // Checking if the seed is a valid integer
    uint seed = atoi(argv[2]);
    if (seed <= 0){
        printf ("Please enter a valid seed.\nSeed is expected to be a positive integer > 0\n");
        exit(EXIT_FAILURE);
    }
    //-----------------------------------------//
    // Number of customers to be served 
    int NCUST = atoi(argv[1]);
    if (NCUST <= 0){
        printf ("Please enter a valid number of customers.\nCustomers are expected to > 0\n");
        exit(EXIT_FAILURE);
    }
    printf("The store is open\n");
    pthread_t* threads;
    threads = (pthread_t*) malloc(NCUST * sizeof(pthread_t));
    //For each customer/order
    for (int i = 0; i<NCUST; i++){
	    uint t_seed = seed * (i+1); // Me tin logiki oti to kathena exei to diko tou seed
        uint pizzas = rand_r(&t_seed)% NORDERHIGH + NORDERLOW;
        struct Params args = {pizzas, i+1};
	    struct Params* argss = &args;
        rc = pthread_create(&threads[i],NULL,order,(void*) argss);
        if (rc != 0){
            printf("Oops something's wrong try again later :P\n");
            exit(EXIT_FAILURE);
        }
        //wait for next order
        uint wait_for_next_order = rand()% TORDERHIGH + TORDERLOW; 
        sleep (wait_for_next_order);
    }
    for(int i = 0; i < NCUST; i++){
            rc = pthread_join(threads[i],NULL);
            if (rc != 0){
                printf("An error occured during thread joining \n");
                exit(EXIT_FAILURE);
            }  
    }
    avgw8time /= NCUST;
    avgtime /= NCUST;
    printf ("Average time of prepation equals: %.2f\nThe most time it took an order to be prepared is %.2f\n",avgtime,max_time);
    printf ("Average wait time : %.2f\nThe most time it took an order to be delivered is %.2f\n", avgw8time, maxw8time);
    printf("Customers where served the store is now closing\n");
    
// Destruction of mutexes and condiotion variables    
    pthread_mutex_destroy(&ck_lock);
    pthread_mutex_destroy(&d_lock);
    pthread_mutex_destroy(&f_lock);
    pthread_mutex_destroy(&c_lock);
    pthread_mutex_destroy(&avg_lock);
    pthread_mutex_destroy(&max_lock);
    pthread_cond_destroy(&f_cond);
    pthread_cond_destroy(&d_cond);
    pthread_cond_destroy(&ck_cond);

    return 0;
}

/* Implementation of the function that is going to be used by the thread */

void* order (void* args){
    float chillingTime;
    struct Params* order_details = (struct Params*)args;
    int id = order_details->id;
    int pizza_number = order_details->noOfPizzas;
    struct timespec sChill_time, start_time, end_time;
    rc = clock_gettime(CLOCK_REALTIME,&start_time);
    /* Checking if there is an available cook to prep the order
    if not then it waits for 1 to be */
    rc = pthread_mutex_lock(&ck_lock);
    while(cooks == 0){
	rc = pthread_mutex_lock(&c_lock);
        printf("H paraggelia %d, den brike diathesimo paraskevasti. Blocked...\n", id);
	rc = pthread_mutex_unlock(&c_lock);
	rc = pthread_cond_wait(&ck_cond, &ck_lock);
    }
    // An available cook begins to prepare an order
    cooks--;
    rc = pthread_mutex_unlock(&ck_lock);
    
    sleep(TPREP*pizza_number); //prepei na polaplasiastei me tis pitses

    // Pizzas entering the furnace 
    rc = pthread_mutex_lock(&f_lock);
    while (ovens == 0)
    {
	    rc = pthread_mutex_lock(&c_lock);
	    printf("H paraggelia %d, den brike diathesimo fourno. Blocked...\n", id);
	    rc = pthread_mutex_unlock(&c_lock);
        rc = pthread_cond_wait(&f_cond, &f_lock);
    }
    // An oven is available so the order goes in
    ovens--;
    rc = pthread_mutex_unlock(&f_lock);

/*---- Increasing available cooks ----*/
    rc = pthread_mutex_lock(&ck_lock);
    cooks++;
    pthread_cond_signal(&ck_cond);
    rc = pthread_mutex_unlock(&ck_lock);

/*---------------baking time---------------*/    
    sleep(TBAKE);
    rc = clock_gettime(CLOCK_REALTIME,&sChill_time);
    
/*-------Waiting for deivery guy to take the order from the furnace------*/
    rc = pthread_mutex_lock(&d_lock);
    while (del == 0){
        rc = pthread_mutex_lock(&c_lock);
	    printf("H paraggelia %d, den brike diathesimo delivera. Waiting...\n", id);
	    rc = pthread_mutex_unlock(&c_lock);
        rc = pthread_cond_wait(&d_cond, &d_lock);
    }
/*---- Decreasing available delivery guy ----*/
    del--;
    rc = pthread_mutex_unlock(&d_lock);

/*---- Increasing available ovens ----*/
    rc = pthread_mutex_lock(&f_lock);
    ovens++;
    pthread_cond_signal(&f_cond);
    rc = pthread_mutex_unlock(&f_lock);
	
/*---- Delivery Time ----*/
    int deliveryTime = (rand() % (THIGH - TLOW + 1)) + TLOW;
    sleep(deliveryTime);
	
/*---- Increasing available delivery guy ----*/
	rc = pthread_mutex_lock(&d_lock);
    del++;
    pthread_cond_signal(&d_cond);
    rc = pthread_mutex_unlock(&d_lock);

	//--------------------------------------------------//
    rc = clock_gettime(CLOCK_REALTIME,&end_time);
    int timeOfPrep = end_time.tv_sec - start_time.tv_sec;
    int timeOfChill = end_time.tv_sec - sChill_time.tv_sec;


/*---- Writing to console ----*/
    rc = pthread_mutex_lock(&c_lock);
    printf("H paraggelia me arithmo <%d> paradothike se <%d> lepta kai kruone <%d> lepta\n",id,timeOfPrep,timeOfChill);
    rc = pthread_mutex_unlock(&c_lock);

/*---- Updating the max variable ----*/
    rc = pthread_mutex_lock(&max_lock);
    max_time = (timeOfPrep > max_time) ? timeOfPrep : max_time;
    rc = pthread_mutex_unlock(&max_lock);

/*---- Updating the total time variable ----*/
    rc = pthread_mutex_lock(&avg_lock);
    avgtime += timeOfPrep;
    rc = pthread_mutex_unlock(&avg_lock);

/*---- Updating the max waiting variable ----*/
    rc = pthread_mutex_lock(&max_w8_lock);
    maxw8time = (timeOfChill > maxw8time) ? timeOfChill : maxw8time;
    rc = pthread_mutex_unlock(&max_w8_lock);

/*---- Updating the total waiting time variable ----*/
    rc = pthread_mutex_lock(&w8_lock);
    avgw8time += timeOfChill;
    rc = pthread_mutex_unlock(&w8_lock);


    pthread_exit(NULL);
}
