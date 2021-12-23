using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
[System.Serializable]

public class Card 
{

    public int id;
    public string cardName;
    public int cost;
    public int power;
    public string cardDescription;

    public int drawXcards;
    public int addXmaxMana;


    public Sprite thisImage;

    public string color;

    public int returnXcards;

    public int healXpower;

    public bool spell;
    public int spellDamage;

    public Card() 
    { 
        
    }

    public Card(int Id, string CardName, int Cost, int Power, string CardDescription,Sprite ThisImage, string Color,int DrawXCards,
        int AddXmaxMana,int ReturnXcards, int HealXpower, bool Spell, int SpellDamage) 
    {
        id = Id;
        cardName = CardName;
        cost = Cost;
        power = Power;
        cardDescription = CardDescription;
        thisImage = ThisImage;
        color = Color;

        drawXcards = DrawXCards;
        addXmaxMana = AddXmaxMana;
        returnXcards = ReturnXcards;

        healXpower = HealXpower;

        spell = Spell;
        spellDamage = SpellDamage;
    }






}
