using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class EndGame : MonoBehaviour
{
    public Text victoryText;
    public GameObject textObject;

    // Start is called before the first frame update
    void Start()
    {
        textObject.SetActive(false);
    }

    // Update is called once per frame
    void Update()
    {
        if(PlayerHp.staticHp <= 0 )
        {
            ChangeScene("Defeat");

            /*textObject.SetActive(true);
            victoryText.text = "You lose";*/
        }
        if (EnemyHp.staticHp <= 0)
        {
            ChangeScene("Victory");

            /*textObject.SetActive(true);
            victoryText.text = "Victory";*/
        }
    }

    public void ChangeScene(string name)
    {
        SceneManager.LoadScene(name);
    }
}
