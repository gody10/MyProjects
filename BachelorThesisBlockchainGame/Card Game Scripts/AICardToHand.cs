using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class AICardToHand : MonoBehaviour
{
    public List<Card> thisCard = new List<Card>();

    


    public int thisId;

    public int id;

    public string cardName;
    public int cost;
    public int power;
    public string cardDescription;

    public Text nameText;
    public Text costText;
    public Text powerText;
    public Text descriptionText;

    public Sprite thisSprite;
    public Image thatImage;

    public Image frame;

    public static int DrawX;
    public int drawXcards;
    public int addXmaxMana;

    public int hurted;
    public int actuallpower;
    public int returnXcards;

    public GameObject Hand;

    public int z = 0;
    public GameObject It;

    public int numberOfCardsInDeck;

    public int healXpower;

    public bool isTarget;
    public GameObject Graveyard;

    public bool thisCardCanBeDestroyed;

    public GameObject cardBack;
    public GameObject AiZone;

    public bool canAttack;
    public bool summoningSickness;

    // Start is called before the first frame update
    void Start()
    {

       

        thisCard[0] = CardDataBase.cardList[thisId];
        Hand = GameObject.Find("Enemy Hand");

        z = 0;
        numberOfCardsInDeck = AI.deckSize;

        Graveyard = GameObject.Find("Enemy Graveyard");
        StartCoroutine(AfterVoidStart());

        AiZone = GameObject.Find("Enemy Zone");

        summoningSickness = true;



    }

    // Update is called once per frame
    void Update()
    {
        
        if(z==0)
        {
            It.transform.SetParent(Hand.transform);
            It.transform.localScale = Vector3.one;
            It.transform.position = new Vector3(transform.position.x, transform
                .position.y, 0);
            It.transform.eulerAngles = new Vector3(25, 0, 0);
            z = 1;
        }

        id = thisCard[0].id;
        cardName = thisCard[0].cardName;
        power = thisCard[0].power;
        cost = thisCard[0].cost;
        cardDescription = thisCard[0].cardDescription;

        thisSprite = thisCard[0].thisImage;


        drawXcards = thisCard[0].drawXcards;
        addXmaxMana = thisCard[0].addXmaxMana;

        returnXcards = thisCard[0].returnXcards;

        healXpower = thisCard[0].healXpower;

        actuallpower = power - hurted;

        thatImage.sprite = thisSprite;

        nameText.text = "" + cardName;
        costText.text = "" + cost;
        powerText.text = "" + actuallpower;
        descriptionText.text = "" + cardDescription;


        if (thisCard[0].color == "Red")
        {
            frame.GetComponent<Image>().color = new Color32(255, 0, 0, 255);
        }
        if (thisCard[0].color == "Blue")
        {
            frame.GetComponent<Image>().color = new Color32(0, 13, 255, 255);
        }
        if (thisCard[0].color == "Yellow")
        {
            frame.GetComponent<Image>().color = new Color32(255, 255, 0, 255);
        }
        if (thisCard[0].color == "White")
        {
            frame.GetComponent<Image>().color = new Color32(255, 255, 255, 255);
        }
        if (thisCard[0].color == "Green")
        {
            frame.GetComponent<Image>().color = new Color32(0, 255, 22, 255);
        }
        if (thisCard[0].color == "Black")
        {
            frame.GetComponent<Image>().color = new Color32(0, 0, 0, 255);
        }


        if(this.tag == "Clone2")
        {

            

            thisCard[0] = AI.staticEnemyDeck[numberOfCardsInDeck - 1];
            numberOfCardsInDeck -= 1;
            AI.deckSize -= 1;
            this.tag = "Untagged";
        }

       if(hurted >= power && thisCardCanBeDestroyed == true)
        {
            this.transform.SetParent(Graveyard.transform);
            hurted = 0;
        }

       if(this.transform.parent == Hand.transform)
        {
            cardBack.SetActive(true);
        }
        else if(this.transform.parent == AiZone.transform)
        {
            cardBack.SetActive(false);
        }

       if(TurnSystem.isYourTurn == false && summoningSickness == false)
        {
            canAttack = true;
        }
        else
        {
            canAttack = false;
        }

       if(TurnSystem.isYourTurn == true && this.transform.parent == AiZone.transform)
        {
            summoningSickness = false;
        }

    }


    public void BeingTarget()
    {
        isTarget = true;

    }


    public void StopBeingTarget()
    {
        isTarget = false;
    }


    IEnumerator AfterVoidStart()
    {
        yield return new WaitForSeconds(1);
        thisCardCanBeDestroyed = true;
    }
}
