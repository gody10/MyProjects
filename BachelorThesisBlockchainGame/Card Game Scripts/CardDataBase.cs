using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CardDataBase : MonoBehaviour
{
    public static List<Card> cardList = new List<Card>();

     void Awake()
    {
        cardList.Add(new Card(0, "None", 0, 0 , "None", Resources.Load<Sprite>("noimage"),"None",0,0,0,0,false,0));
        cardList.Add(new Card(1, "Elf", 2, 1000, "Draw 2 Cards", Resources.Load<Sprite>("noimage"), "Red",2,0,0,0, false, 0));
        cardList.Add(new Card(2, "Dwarf", 3, 3000, "Add 1 Max Mana", Resources.Load<Sprite>("noimage"), "Blue",0,1,0,0, false, 0));
        cardList.Add(new Card(3, "Human", 5, 6000, "Add 3 Max Mana", Resources.Load<Sprite>("noimage"),"White",0,3,0,0, false, 0));
        cardList.Add(new Card(4, "Demon", 1, 7000, "Draw 1 Card", Resources.Load<Sprite>("noimage"), "Black",1,0,0,0, false, 0));
        cardList.Add(new Card(5, "Slime", 2, 500, "You are useless", Resources.Load<Sprite>("noimage"),"Green",0,0,0,0, false, 0));
        cardList.Add(new Card(6, "Dog", 1, 200, "Attacks", Resources.Load<Sprite>("noimage"), "Yellow", 0, 0,0,0, false, 0));
        cardList.Add(new Card(7, "Priest", 1, 200, "Return 1 from Graveyard", Resources.Load<Sprite>("noimage"), "White", 0, 0, 1,0, false, 0));
        cardList.Add(new Card(8, "Healer", 1, 200, "Heals for 2000 hp", Resources.Load<Sprite>("noimage"), "White", 0, 0, 0, 2000, false, 0));
        cardList.Add(new Card(9, "Treeman", 7, 4000, "Heals for 3000 hp", Resources.Load<Sprite>("noimage"), "Green", 0, 0, 0, 2000, false, 0));
        cardList.Add(new Card(10, "Woodelf", 3, 2000, "Draw 2", Resources.Load<Sprite>("noimage"), "Green", 2, 0, 0, 2000, false, 0));


        //Spell Cards
        cardList.Add(new Card(11, "Fireball", 3, 0, "Deal 3000 damage", Resources.Load<Sprite>("Fireball"), "Red", 0, 0, 0,0, true, 3000));
        cardList.Add(new Card(12, "Heal", 4, 0, "Heal 3000", Resources.Load<Sprite>("Heal"), "Green", 0, 0, 0, 3000, true, 0));


    }

}
