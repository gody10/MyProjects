using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class PlayerDeck : MonoBehaviour
{

    public List<Card> deck = new List<Card>();
    public List<Card> container = new List<Card>();
    public static List<Card> staticDeck = new List<Card>();

    public int x;
    public static int deckSize;
   


    public GameObject cardInDeck1;
    public GameObject cardInDeck2;
    public GameObject cardInDeck3;
    public GameObject cardInDeck4;

    public GameObject CardToHand;
    public GameObject CardBack;
    public GameObject Deck;

    public GameObject[] Clones;

    public GameObject Hand;


    public Text LoseText;
    public GameObject LoseTextGameObject;

    private void Awake()
    {
        Shuffle();
    }

    // Start is called before the first frame update
    void Start()
    {
        x = 0;

        deckSize = 40;

      /*  for (int i = 0; i < deckSize; i++) {
            x = Random.RandomRange(1, 13);
            deck[i] = CardDataBase.cardList[x];
        }*/

        for(int i =0; i<8;i++)
        {
            if(PlayerPrefs.GetInt("deck" + i, 0) > 0)
            {
                for(int j=1;j<= PlayerPrefs.GetInt("deck" + i,0);j++)
                {
                    deck[x] = CardDataBase.cardList[i];
                    x++;
                }
            }
            
        }

        Shuffle();

        StartCoroutine(StartGame());
    }

    // Update is called once per frame
    void Update()
    {
        if(deckSize <= 0)
        {
            LoseTextGameObject.SetActive(true);
            LoseText.text = "You Lose";
        }

        staticDeck = deck;


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

        if(ThisCard.drawX > 0)
        {
            StartCoroutine(Draw(ThisCard.drawX));
            ThisCard.drawX = 0;
        }

        if(TurnSystem.startTurn == true)
        {
            StartCoroutine(Draw(1));
            TurnSystem.startTurn = false;
        }

    }

    IEnumerator Example() {
        yield return new WaitForSeconds(1);
        Clones = GameObject.FindGameObjectsWithTag("Clone2");

        foreach(GameObject Clone2 in Clones)
        {
            Destroy(Clone2);
        }

    }


    IEnumerator StartGame()
    {
        for(int i=0;i<=4;i++)
        {
            yield return new WaitForSeconds(1);
            Instantiate(CardToHand, transform.position, transform.rotation,Hand.transform);
        }

    }


    public void Shuffle() {
        for (int i = 0; i < deckSize; i++) {
            container[0] = deck[i];
            int randomIndex = Random.Range(i, deckSize);
            deck[i] = deck[randomIndex];
            deck[randomIndex] = container[0];

        }
        var tr = Instantiate(CardBack, transform.position, transform.rotation);     
        tr.gameObject.tag = "Clone2";
        StartCoroutine(Example());

    }


    public static int getDeckSize() {
        return deckSize;
    }


    IEnumerator Draw(int x)
    {
        for(int i=0;i<x;i++)
        {
            yield return new WaitForSeconds(1);
            Instantiate(CardToHand, transform.position, transform.rotation);
        }

    }
    









}
