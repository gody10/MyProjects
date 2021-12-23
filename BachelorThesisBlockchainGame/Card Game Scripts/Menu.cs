using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
public class Menu : MonoBehaviour
{

    public string play;
    public string deck;
    public string collection;
    public string settings;

    public string menu;

    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }


    public void LoadPlay()
    {
        SceneManager.LoadScene(play);
    }

    public void LoadDeck()
    {
        SceneManager.LoadScene(deck);
    }

    public void LoadCollection()
    {
        SceneManager.LoadScene(collection);
    }

    public void ReturnToMenu()
    {
        SceneManager.LoadScene(menu);
    }

    public void ExitApplication()
    {
        
        Application.Quit();
    }
}
