
package entities;


public class Vocabulary {

    private Integer tf;
    private Integer nr;
    private boolean baseDeDatos;
    private Integer id;

    public Vocabulary(){
        this( 1, 0, false, -1 );
    }


    public Vocabulary(Integer tf, Integer nr, boolean baseDeDatos, Integer id) {
        this.tf = tf;
        this.nr = nr;
        this.baseDeDatos = baseDeDatos;
        this.id = id;
    }

    public Integer getTf() {
        return tf;
    }

    public void setTf(Integer tf) {
        this.tf = tf;
    }

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public boolean isBaseDeDatos() {
        return baseDeDatos;
    }

    public void setBaseDeDatos(boolean baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Valor{" + "tf=" + tf + ", nr=" + nr + ", baseDeDatos=" + baseDeDatos + '}';
    }

}

