import java.io.Serializable;

public class Animal implements Serializable {

    private int _id;
    private String species;
    private String color;
    private float size;
    private String description;

    public Animal(String species, String color, float size, String description){
        super();
        this.species = species;
        this.color = color;
        this.size = size;
        this.description = description;
    }

    @Override
    public String toString(){
        return "Zwierze: [id:" + _id + ", gatunek: " + species + "kolor: " + color + "wielkość: " + size + " ] ";
    }

    public String getSpecies(){
        return species;
    }
    public String getColor(){
        return color;
    }
    public float getSize(){
        return size;
    }
    public String getDescription(){
        return description;
    }
    public int get_id(){
        return _id;
    }
    public void set_id(int _id){
        this._id = _id;
    }


}

