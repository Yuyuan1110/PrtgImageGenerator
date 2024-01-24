package org.acom.beans;

import java.util.ArrayList;

public class NameAndID {
    ArrayList<String> objName;
    ArrayList<String> objID;

    public NameAndID() {
    }

    public NameAndID(ArrayList<String> objName, ArrayList<String> objID) {
        this.objName = objName;
        this.objID = objID;
    }

    public ArrayList<String> getObjName() {
        return objName;
    }

    public void setObjName(ArrayList<String> objName) {
        this.objName = objName;
    }

    public ArrayList<String> getObjID() {
        return objID;
    }

    public void setObjID(ArrayList<String> objID) {
        this.objID = objID;
    }

    @Override
    public String toString() {
        return "NameAndID{" +
                "objName=" + objName +
                ", objID=" + objID +
                '}';
    }
}
