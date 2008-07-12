package no.lau;

import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Assert;
import net.sf.oval.guard.Pre;

@net.sf.oval.guard.Guarded
public class BusinessObject {

    private String name;

    public void setName(@NotNull String name) {
        this.name = name;
    }

   @Pre(expr = "return _args[0] instanceof no.lau.kpro.model.Circle", lang = "groovy")
    public void runSomething(Object input) {
        System.out.println("input2 = " + input);
    }
}