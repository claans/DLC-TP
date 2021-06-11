/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Mu_Cl
 */
public class Document {
    
    private String nameDocument;
    private String pathDocument;
    
    public Document(String nameDocument, String pathDocument ) {
        this.nameDocument = nameDocument;
        this.pathDocument = pathDocument;
    }

    public String getName( ) {
        return nameDocument;
    }

    public void setName(String name) {
        this.nameDocument = name;
    }

    public String getPath() {
        return pathDocument + this.nameDocument;
    }

    public void setPath(String path) {
        this.pathDocument = path;
    }
}
