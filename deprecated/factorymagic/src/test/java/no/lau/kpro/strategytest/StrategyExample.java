package no.lau.kpro.strategytest;

//StrategyExample test application
public class StrategyExample {

    public static void main(String[] args) {
        Context context;

        // Three contexts following different strategies
        context = new Context(new PinkStrategyA());
        context.execute();

        context = new Context(new YellowStrategyB());
        context.execute();

        context = new Context(new BlueStrategyC());
        context.execute();


    }
}

// The classes that implement a concrete strategy should implement this
// The context class uses this to call the concrete strategy
interface IStrategy {
    void execute();
}

// Implements the algorithm using the strategy interface
class PinkStrategyA implements IStrategy {
    public void execute() {
        System.out.println("Called PinkStrategyA.execute()");
    }
}

class YellowStrategyB implements IStrategy {
    public void execute() {
        System.out.println("Called YellowStrategyB.execute()");
    }
}

class BlueStrategyC implements IStrategy {
    public void execute() {
        System.out.println("Called BlueStrategyC.execute()");
    }
}

// Configured with a ConcreteStrategy object and maintains a reference to a Strategy object
class Context {
    IStrategy strategy;

    // Constructor
    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        this.strategy.execute();
    }
}