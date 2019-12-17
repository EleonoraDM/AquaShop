package repositories;

import models.decorations.Decoration;
import models.decorations.Ornament;
import models.decorations.Plant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DecorationRepository implements Repository {
    private Collection<Decoration> decorations;

    public DecorationRepository() {
        this.decorations = new ArrayList<>();
    }

    public Collection<Decoration> getDecorations() {
        return Collections.unmodifiableCollection(this.decorations);
    }

    @Override
    public void add(Decoration decoration) {
        this.decorations.add(decoration);
    }

    @Override
    public boolean remove(Decoration decoration) {
        return this.decorations.remove(decoration);
    }

    @Override
    public Decoration findByType(String type) {
        Decoration decoration;

        if (type.equals("Ornament")) {
           decoration =  this.decorations.
                   stream().
                   filter(d -> d instanceof Ornament).
                   findFirst().
                   orElse(null);
        }else {
            decoration =  this.decorations.
                    stream().
                    filter(d -> d instanceof Plant).
                    findFirst().
                    orElse(null);
        }
        return decoration;
    }
}
