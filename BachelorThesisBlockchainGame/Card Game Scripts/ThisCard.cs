using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;
using System;

public class ThisCard : MonoBehaviour
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

    public bool cardBack;
    CardBack CardBackScript;

    public GameObject Hand;

    public int numberOfCardsInDeck;

    public bool canBeSummon;
    public bool summoned;
    public GameObject battleZone;

    public static int drawX;
    public int drawXcards;
    public int addXmaxMana;

    public GameObject attackBorder;

    public GameObject Target;
    public GameObject Enemy;

    public bool summoningSickness;
    public bool cantAttack;

    public bool canAttack;

    public static bool staticTargeting;
    public static bool staticTargetingEnemy;

    public bool targeting;
    public bool targetingEnemy;

    public bool onlyThisCardAttack;

    public GameObject summonBorder;

    public bool canBeDestroyed;
    public GameObject Graveyard;
    public bool beInGraveyard;

    public int hurted;
    public int actuallpower;
    public int returnXcards;
    public bool useReturn;

    public static bool UcanReturn;

    public int healXpower;
    public bool canHeal;

    public GameObject EnemyZone;
    public AICardToHand aiCardToHand;

    public bool spell;
    public int spellDamage;

    public bool dealDamage;

    public bool stopDealingDamage;

    // Start is called before the first frame update
    void Start()
    {
        
        CardBackScript = GetComponent<CardBack>();
        thisCard[0] = CardDataBase.cardList[thisId];

        numberOfCardsInDeck = PlayerDeck.getDeckSize();

        canBeSummon = false;
        summoned = false;
        hurted = 0;
        drawX = 0;

        canAttack = false;
        summoningSickness = true;

        Enemy = GameObject.Find("EnemyCircle");
        

        targeting = false;
        targetingEnemy = false;

        canHeal = true;

        EnemyZone = GameObject.Find("Enemy Zone");

    }

    // Update is called once per frame
    void Update()
    {
        Hand = GameObject.Find("Hand");
        if (this.transform.parent == Hand.transform.parent)
        {
            cardBack = false;
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

        spell = thisCard[0].spell;
        spellDamage = thisCard[0].spellDamage;

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


        CardBackScript.UpdateCard(cardBack);


        if(this.tag == "Clone")
        {
            thisCard[0] = PlayerDeck.staticDeck[numberOfCardsInDeck - 1];
            numberOfCardsInDeck -=1;

            PlayerDeck.deckSize -= 1;
            
             cardBack = false;
            this.tag = "Untagged";
        }

        if(this.tag != "Deck" && this.tag != "Clone2"){

            if (TurnSystem.currentMana >= cost && summoned == false && beInGraveyard == false && TurnSystem.isYourTurn == true)
            {
                canBeSummon = true;

            }
            else canBeSummon = false;

            if (canBeSummon == true)
            {
                gameObject.GetComponent<Draggable>().enabled = true;

            } else
                gameObject.GetComponent<Draggable>().enabled = false;

            battleZone = GameObject.Find("Zone");


            if (summoned == false && this.transform.parent == battleZone.transform)
            {
                Summon();
            }

            if(canAttack == true && beInGraveyard == false)
            {
                attackBorder.SetActive(true);
            }
            else
            {
                attackBorder.SetActive(false);
            }

            if(TurnSystem.isYourTurn == false && summoned == true)
            {
                summoningSickness = false;
                cantAttack = false;
            }

            if(TurnSystem.isYourTurn == true && summoningSickness == false && cantAttack == false)
            {
                canAttack = true;
            }else
            {
                canAttack = false;
            }

            targeting = staticTargeting;
            targetingEnemy = staticTargetingEnemy;

            if(targetingEnemy == true)
            {
                Target = Enemy;
            }
            else
            {
                Target = null;
            }

            if(targeting == true &&  onlyThisCardAttack == true)
            {
                Attack();
            }

            if (canBeSummon == true || UcanReturn == true && beInGraveyard == true)
            {
                summonBorder.SetActive(true);
            }else
            {
                summonBorder.SetActive(false);

            }

            if(actuallpower < 0 && spell == false)
            {
                Destroy();
            }

            if (returnXcards > 0 && summoned == true && useReturn == false && TurnSystem.isYourTurn == true)
            {
                Return(returnXcards);
                useReturn = true;
            }


            if(TurnSystem.isYourTurn == false)
            {
                UcanReturn = false;

            }

            if (canHeal == true && summoned == true)
            {
                Heal();
                canHeal = false;
            }

            if(spellDamage > 0)
            {
                dealDamage = true;
            }

            if(dealDamage == true && this.transform.parent == battleZone.transform)
            {
                attackBorder.SetActive(true);
            }/*
            else
            {
                attackBorder.SetActive(false);
            }*/

            if (dealDamage == true && this.transform.parent == battleZone.transform)
            {
                dealxDamage(spellDamage);
            }

            if(stopDealingDamage == true)
            {
                attackBorder.SetActive(false);
                dealDamage = false;
            }

            if(this.transform.parent == battleZone.transform && spell == true && dealDamage==false)
            {
                Destroy();
            }

        }
    }



    public void Summon()
    {
        TurnSystem.currentMana -= cost;
        summoned = true;

        MaxMana(addXmaxMana);
        drawX = drawXcards;
    }


    public void MaxMana(int x)
    {
        TurnSystem.maxMana += x;

    }

    public void Attack()
    {
        if(canAttack == true && summoned == true)
        {
            if(Target != null)
            {
                if(Target == Enemy)
                {
                    
                    EnemyHp.staticHp -= power;
                  
                    targeting = false;
                    cantAttack = true;
                }   
            }
            else
            {
                foreach(Transform child in EnemyZone.transform)
                {
                    if(child.GetComponent<AICardToHand>().isTarget == true)
                    {
                        child.GetComponent<AICardToHand>().hurted = power;
                        hurted = child.GetComponent<AICardToHand>().power;
                        cantAttack = true;
                    }
                }
            }
        }

        
    }

    public void UntargetEnemy()
    {
        staticTargetingEnemy = false;
    }

    public void TargetEnemy()
    {
        staticTargetingEnemy = true;

    }

    public void StartAttack()
    {
        staticTargeting = true;
    }

    public void StopAttack()
    {
        staticTargeting = false;
    }

    public void OneCardAttack()
    {
        onlyThisCardAttack = true;
    }

    public void OneCardAttackStop()
    {
        onlyThisCardAttack = false;
    }

    public void Destroy()
    {
        Graveyard = GameObject.Find("Graveyard");
        canBeDestroyed = true; 
        if(canBeDestroyed == true)
        {
            this.transform.SetParent(Graveyard.transform);
            canBeDestroyed = false;
            summoned = false;
            beInGraveyard = true;
            hurted = 0;
        }
    }

    public void Return(int x)
    {
        for(int i=0; i<x; i++)
        {
            ReturnCard();
        }
    }
    public void ReturnCard()
        {
            UcanReturn = true;
        }


    public void ReturnThis()
    {
        if(beInGraveyard == true && UcanReturn == true)
        {
            this.transform.SetParent(Hand.transform);
            UcanReturn = false;
            beInGraveyard = false;
            summoningSickness = true;
        }
    }

    public void Heal()
    {
        PlayerHp.staticHp += healXpower;
    }


    public void dealxDamage(int x)
    {
        if(Target != null)
        {
            if(Target == Enemy && stopDealingDamage == false && Input.GetMouseButton(0))
            {
                EnemyHp.staticHp -= spellDamage;
                stopDealingDamage = true;
            }
        }
        else
        {
            foreach(Transform child in EnemyZone.transform)
                {
                if (child.GetComponent<AICardToHand>().isTarget == true && Input.GetMouseButton(0))
                {
                    child.GetComponent<AICardToHand>().hurted += spellDamage;
                    stopDealingDamage = true;
                   
                }
            }
        }
    }


}
