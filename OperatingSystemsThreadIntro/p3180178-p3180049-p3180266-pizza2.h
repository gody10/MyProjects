#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>
#include <stdlib.h>
typedef unsigned int uint;

#define NCOOKS 2
#define NOVEN 5
#define NDELIVER 10
#define TORDERLOW 1
#define TORDERHIGH 5
#define NORDERLOW 1
#define NORDERHIGH 5
#define TPREP 1
#define TBAKE 10
#define TLOW 5
#define THIGH 15
 
/* @args and @order are void pointers because it's prerequisite
from the pthreads library */
void* order(void* args);
