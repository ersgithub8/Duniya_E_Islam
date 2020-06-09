package ru.ironcodes.islamicwikipedia;

import java.io.Serializable;


/**
 * Created by IronCodes on 11.04.2019.
 * Website : http://ironcodes.tech/
 * "All rights reserved ©2014-©2019"
 * Support & Emails : dev@ironcodes.tech , ironcodesdev@gmail.com
 */


public class Azkar implements Serializable {

    // private variables
    int _id;
    String _name;
    String _azkar;
    String _category;
    String _fileName;
    String _fav;
    String _count;

    // Empty constructor
    public Azkar() {

    }

    // Azkar constructor
    public Azkar(int id, String name, String azkar, String category,
                 String fileName, String fav) {
        this._id = id;
        this._name = name;
        this._azkar = azkar;
        this._category = category;
        this._fileName = fileName;
        this._fav = fav;

    }

    // Author constructor
    public Azkar(String name, String fileName, String count) {

        this._name = name;

        this._fileName = fileName;

        this._count = count;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting Azkar
    public String getAzkar() {
        return this._azkar;
    }

    // setting Azkar
    public void setAzkar(String azkar) {
        this._azkar = azkar;
    }

    // getting categoty
    public String getCategory() {
        return this._category;
    }

    // setting categoty
    public void setCategory(String category) {
        this._category = category;
    }

    // getting fileName
    public String getFileName() {
        return this._fileName;
    }

    // setting fileName
    public void setFileName(String fileName) {
        this._fileName = fileName;
    }

    // getting favorite
    public String getFav() {
        return this._fav;
    }

    // setting favorite
    public void setFav(String fav) {
        this._fav = fav;
    }

    // getting counter
    public String getCount() {
        return this._count;
    }

    // setting counter
    public String setCount(String count) {
        return this._count = count;
    }

}
