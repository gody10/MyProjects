using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class WindowInDeck : MonoBehaviour
{

    public GameObject Panel;
    public Text nameText;

    public int id;

    public int quantityOf;

    public GameObject Creator;



    // Start is called before the first frame update
    void Start()
    {
        Panel = GameObject.Find("Panel");
        Creator = GameObject.Find("Collection UI Panel");
        transform.SetParent(Panel.transform);
        transform.localScale = new Vector3(1,1,1);

        id = DeckCreator.lastAdded;

    }

    // Update is called once per frame
    void Update()
    {
        quantityOf = Creator.GetComponent<DeckCreator>().quantity[id];
        nameText.text = CardDataBase.cardList[id].cardName + " X " + quantityOf;
    }
}
