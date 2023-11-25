import java.util.ArrayList;
import java.util.List;

public class Serpent {
    private List<SerpentPart> segments;

    public Serpent() {
        segments = new ArrayList<>();
        
    }

    public List<SerpentPart> getSegments() {
        return segments;
    }

    public void setHeadPosition(double x, double y) {
        SerpentPart head = segments.get(0);
        head.setX(x);
        head.setY(y);
    }

    // Ajouter methode pour les colisions, la mort etc
}