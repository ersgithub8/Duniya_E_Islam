package ru.ironcodes.islamicwikipedia;

/**
 *All Copy Rights Reserved by IronCodes 15/11/2018
 */
public class Category {
    // private variables

    String _name;
    String _fileName;
    String _count;

    // Empty constructor
    public Category() {

    }

    // constructor
    public Category(String name, String fileName, String count) {

        this._name = name;
        this._fileName = fileName;
        this._count = count;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting fileName
    public String getFileName() {
        return this._fileName;
    }

    // setting fileName
    public void setFileName(String fileName) {
        this._fileName = fileName;
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
