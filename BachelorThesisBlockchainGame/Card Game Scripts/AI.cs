using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AI : MonoBehaviour
{

    public List<Card> Enemydeck = new List<Card>();
    public List<Card> container = new List<Card>();
    public static List<Card> staticEnemyDeck = new List<Card>();

    public List<Card> cardsInHand = new List<Card>();

    public List<Card> cardsInZone = new List<Card>();

    public GameObject Hand;
    public GameObject Zone;
    public GameObject Graveyard;

    public int x;
    public static int deckSize;


    public GameObject cardInDeck1;
    public GameObject cardInDeck2;
    public GameObject cardInDeck3;
    public GameObject cardInDeck4;

    public GameObject CardToHand;
   

    public GameObject[] Clones;

    public static bool draw;

    public GameObject CardBack;

    public int currentMana;

    public bool[] AiCanSummon;

    public bool drawPhase;
    public bool summonPhase;
    public bool attackPhase;
    public bool endPhase;

    public int[] cardsID;

    public int summonThisId;

    public AICardToHand aiCardToHand;

    public int summonID;

    public int howManyCards;

    public bool[] canAttack;
    public static bool AiEndPhase;

    private void Awake()
    {
        Shuffle();
    }

    // Start is called before the first frame update
    void Start()
    {

        StartCoroutine(WaitFiveSeconds());

        StartCoroutine(StartGame());

        Hand = GameObject.Find("Enemy Hand");
        Zone = GameObject.Find("Enemy Zone");
        Graveyard = GameObject.Find("Enemy Graveyard");

        x = 0;
        deckSize = 40;
        draw = true;

        for(int i=0; i<deckSize;i++)
        {
            x = Random.Range(1, 8);
            Enemydeck[i] = CardDataBase.cardList[x];
        }





    }

    // Update is called once per frame
    void Update()
    {
        staticEnemyDeck = Enemydeck;
        

        if (deckSize < 30)
        {
            cardInDeck1.SetActive(false);
        }
        if (deckSize < 20)
        {
            cardInDeck2.SetActive(false);
        }
        if (deckSize < 2)
        {
            cardInDeck3.SetActive(false);
        }
        if (deckSize < 1)
        {
            cardInDeck4.SetActive(false);
        }

        if (ThisCard.drawX > 0)
        {
            StartCoroutine(Draw(ThisCard.drawX));
            ThisCard.drawX = 0;
        }

        if (TurnSystem.startTurn == false && draw == false)
        {
            StartCoroutine(Draw(1));
            draw = true;
        }

        currentMana = TurnSystem.currentEnemyMana;

        if(0 == 0)
        {
            int j = 0;
            howManyCards = 0;
            foreach(Transform child in Hand.transform)
            {
                howManyCards++;
            }
            foreach(Transform child in Hand.transform)
            {
                cardsInHand[j] = child.GetComponent<AICardToHand>().thisCard[0];
                j++;
            }

            for(int i=0;i<40;i++)
            {
                if(i >= howManyCards)
                {
                    cardsInHand[i] = CardDataBase.cardList[0];
                }
            }
            j = 0;

        }

        if(TurnSystem.isYourTurn == false)
        {
            for(int i =0;i<40;i++)
            {
                if(cardsInHand[i].id != 0 )
                {
                    if(currentMana >= cardsInHand[i].cost)
                    {
                        AiCanSummon[i] = true;
                    }
                }
            }
        }
        else
        {
            for(int i=0; i<40;i++)
            {
                AiCanSummon[i] = false;
            }
        }



        if (TurnSystem.isYourTurn == false)
        {
            drawPhase = true;
        }

        if(drawPhase == true && summonPhase == false && attackPhase == false)
        {
            StartCoroutine(WaitForSummonPhase());
        }

        if(TurnSystem.isYourTurn == true)
        {
            drawPhase = false;
            summonPhase = false;
            attackPhase = false;
            endPhase = false;
        }

        if (summonPhase == true)
        {
            summonID = 0;
            summonThisId = 0;

            int index = 0;
            for (int i = 0; i < 40; i++)
            {
                if (AiCanSummon[i] == true)
                {
                    cardsID[index] = cardsInHand[i].id;
                    index++;
                }
            }



            for (int i = 0; i < 40; i++)
            {
                if (cardsID[i] != 0)
                {
                    if (cardsID[i] > summonID)
                    {
                        summonID = cardsID[i];
                    }
                }
            }



            summonThisId = summonID;

            foreach (Transform child in Hand.transform)
            {
                if (child.GetComponent<AICardToHand>().id == summonThisId && CardDataBase.cardList[summonThisId].cost <= currentMana)
                {
                    child.transform.SetParent(Zone.transform);
                    TurnSystem.currentEnemyMana -= CardDataBase.cardList[summonThisId].cost;
                    break;
                }
            }

            summonPhase = false;
            attackPhase = true;
        }


        if(0==0)
        {
            int k = 0;
            int howManyCards2 = 0;
            foreach (Transform child in Zone.transform)
            {
                howManyCards2++;
            }
            foreach (Transform child in Zone.transform)
            {
                canAttack[k] = child.GetComponent<AICardToHand>().canAttack;
                k++;
            }

            for (int i = 0; i < 40; i++)
            {
                if (i >= howManyCards2)
                {
                    canAttack[i] = false;
                }
            }
            k = 0;
        }

        if (0 == 0)
        {
            int l = 0;
            int howManyCards3 = 0;
            foreach (Transform child in Zone.transform)
            {
                howManyCards3++;
            }
            foreach (Transform child in Zone.transform)
            {
                cardsInZone[l] = child.GetComponent<AICardToHand>().thisCard[0];
                l++;
            }

            for (int i = 0; i < 40; i++)
            {
                if (i >= howManyCards3)
                {
                    cardsInHand[i] = CardDataBase.cardList[0];
                }
            }
            l = 0;
        }

        if(attackPhase == true && endPhase == false)
        {
            for(int i=0;i<40;i++)
            {
                if(canAttack[i] == true)
                {
                    PlayerHp.staticHp -= cardsInZone[i].power;
                }
            }

            endPhase = true;
        }

        if(endPhase == true)
        {
            AiEndPhase = true;
        }

    }


    public void Shuffle()
    {
        for(int i =0;i<deckSize;i++)
        {
            container[0] = Enemydeck[i];
            int randomIndex = Random.Range(i, deckSize);
            Enemydeck[i] = Enemydeck[randomIndex];
            Enemydeck[randomIndex] = container[0];
        }

        var tr = Instantiate(CardBack, transform.position, transform.rotation);
        tr.gameObject.tag = "Clone2";
    
        StartCoroutine(ShuffleNow());
     
    }


    IEnumerator StartGame() 
    {
        for(int i=0; i<=4;i++)
        {
            yield return new WaitForSeconds(1);
            Instantiate(CardToHand, transform.position, transform.rotation,Hand.transform);

        }
    }


    IEnumerator ShuffleNow()
    {
        yield return new WaitForSeconds(1);
        Clones = GameObject.FindGameObjectsWithTag("Clone2");

        foreach(GameObject Clone2 in Clones)
        {
            Destroy(Clone2);
        }

    }


    IEnumerator Draw(int x)
    {
        for(int i = 0;i<x;i++)
        {
            yield return new WaitForSeconds(1);
            Instantiate(CardToHand, transform.position, transform.rotation);
        }
    }


    IEnumerator WaitFiveSeconds()
    {
        yield return new WaitForSeconds(5);
       
    }

    IEnumerator WaitForSummonPhase()
    {
        yield return new WaitForSeconds(5);
        summonPhase = true;
    }

}
