using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class TurnSystem : MonoBehaviour
{

    public static bool isYourTurn;
    public int yourTurn;
    public int yourOpponentTurn;
    public Text turnText;


    public static int maxMana;
    public static int currentMana;
    public Text manaText;

    public static bool startTurn;

    public int random;

    public bool turnEnd;
    public Text timerText;
    public int seconds;
    public bool timerStart;

    public static int maxEnemyMana;
    public static int currentEnemyMana;
    public Text enemyManaText;

    // Start is called before the first frame update
    void Start()
    {
      
        StartGame();

        seconds = 60;
        timerStart = true;


    }

    // Update is called once per frame
    void Update()
    {
        if (isYourTurn == true)
        {
            turnText.text = "Your Turn";
        }
        else
        {
            turnText.text = "Opponent Turn";
        }
        manaText.text = currentMana + "/" + maxMana;

        if(isYourTurn == true && seconds >0 && timerStart == true)
        {
            StartCoroutine(Timer());
            timerStart = false;

        }

        if(seconds == 0 && isYourTurn == true)
        {
            EndYourTurn();
            timerStart = true;
            seconds = 60;
        }

        timerText.text = seconds + "";

        if(isYourTurn == false && seconds > 0 && timerStart == true)
        {
            StartCoroutine(EnemyTimer());
            timerStart = false;
        }

        if(seconds == 0 && isYourTurn == false)
        {
            EndOpponentTurn();
            timerStart = true;
            seconds = 60;
        }

        enemyManaText.text = currentEnemyMana + "/" + maxEnemyMana;

        if(AI.AiEndPhase == true)
        {
            EndOpponentTurn();
            AI.AiEndPhase = false;
        }

    }


    public void EndYourTurn()
    {
        isYourTurn = false;
        yourOpponentTurn += 1;

        maxEnemyMana += 1;
        currentEnemyMana += 1;

        currentEnemyMana = maxEnemyMana;

        AI.draw = false;
    }

    public void EndOpponentTurn()
    {
        isYourTurn = true;
        yourTurn += 1;

        maxMana += 1;
        currentMana = maxMana;

        startTurn = true;
    }

    public void StartGame()
    {
        random = Random.Range(0, 2);
        if(random == 0)
        {
            isYourTurn = true;
            yourTurn = 1;
            yourOpponentTurn = 0;

            maxMana = 1;
            currentMana = 1;

            maxEnemyMana = 0;
            currentEnemyMana = 0;

            startTurn = false;
        }
        else if(random == 1)
        {
            isYourTurn = false;
            yourTurn = 0;
            yourOpponentTurn = 1;

            maxMana = 0;
            currentMana = 0;

            maxEnemyMana = 1;
            currentEnemyMana = 1;

        }
    }    

    IEnumerator Timer()
    {
        if(isYourTurn == true && seconds >0)
        {
            yield return new WaitForSeconds(1);
            seconds --;
            StartCoroutine(Timer());
        }
    }

    IEnumerator EnemyTimer()
    {
        if (isYourTurn == false && seconds > 0)
        {
            yield return new WaitForSeconds(1);
            seconds--;
            StartCoroutine(EnemyTimer());
        }
    }

}

