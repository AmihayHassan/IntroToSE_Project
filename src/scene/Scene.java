package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

// imports for the xml bonus
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;


/**
 * this class represents a scene in the real life - containing different geometries to be seen by camera
 */
public class Scene {

    /**
     * @member _name - the point the vector points to from (0,0,0)
     * @member background -  color of background in photo
     * @member ambientLight -  the surrounding light in the room
     * @member geometries - the shapes in scene
     */
    public final String _name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight(new Color(192, 192, 192), 1.d);
    ;
    public Geometries geometries = null;

    /**
     * constructor
     *
     * @param name - name of scene
     */
    public Scene(String name) {
        _name = name;
        geometries = new Geometries();
    }

    public Scene XMLtoScene(String path) {
        return null;
    }

//chaining methods

    /**
     * setter - chaining method style
     *
     * @param background - color of background
     * @return this instance
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * setter - chaining method style
     *
     * @param ambientLight - the surroundong light in room
     * @return this instance
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * setter - chaining method style
     *
     * @param geometries - shapes in photo
     * @return this instance
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

}