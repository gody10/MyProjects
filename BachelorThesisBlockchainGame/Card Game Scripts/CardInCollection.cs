using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CardInCollection : MonoBehaviour
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

    public bool beGrey;
    public GameObject frame;

    // Start is called before the first frame update
    void Start()
    {
        thisCard[0] = CardDataBase.cardList[thisId];
    }

    // Update is called once per frame
    void Update()
    {
        thisCard[0] = CardDataBase.cardList[thisId];

        id = thisCard[0].id;
        cardName = thisCard[0].cardName;
        power = thisCard[0].power;
        cost = thisCard[0].cost;
        cardDescription = thisCard[0].cardDescription;

        thisSprite = thisCard[0].thisImage;

        nameText.text = "" + cardName;
        costText.text = "" + cost;
        powerText.text = "" + power;
        descriptionText.text = "" + cardDescription;
        thatImage.sprite = thisSprite;

        if(beGrey == true)
        {

            frame.GetComponent<Image>().color = new Color32(155, 155, 155, 255);
        }
        else
        {
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
             
        }
    }
}
