package edu.iu.habahram.GumballMachine.model;

public class SoldState implements IState {
    private final IGumballMachine gumballMachine;

    public SoldState(IGumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public TransitionResult insertQuarter() {
        return new TransitionResult(
                false,
                "Please wait, we're already giving you a gumball",
                gumballMachine.getTheStateName(),
                gumballMachine.getCount()
        );
    }

    @Override
    public TransitionResult ejectQuarter() {
        return new TransitionResult(
                false,
                "Sorry, you already turned the crank",
                gumballMachine.getTheStateName(),
                gumballMachine.getCount()
        );
    }

    @Override
    public TransitionResult turnCrank() {
        return new TransitionResult(
                false,
                "Turning twice doesn't get you another gumball",
                gumballMachine.getTheStateName(),
                gumballMachine.getCount()
        );
    }

    @Override
    public TransitionResult dispense() {
        // actually dispense one gumball
        gumballMachine.releaseBall();
        String message = "A gumball comes rolling out the slot";
        // choose next state
        if (gumballMachine.getCount() > 0) {
            gumballMachine.changeTheStateTo(GumballMachineState.NO_QUARTER);
        } else {
            gumballMachine.changeTheStateTo(GumballMachineState.OUT_OF_GUMBALLS);
            message += ", and the machine is now sold out";
        }
        return new TransitionResult(
                true,
                message,
                gumballMachine.getTheStateName(),
                gumballMachine.getCount()
        );
    }

    @Override
    public String getTheName() {
        return GumballMachineState.GUMBALL_SOLD.name();
    }

    @Override
    public TransitionResult refill(int countToAdd) {
        gumballMachine.addGumballs(countToAdd);
        return new TransitionResult(
                true,
                countToAdd + " gumball(s) added; still in " + getTheName(),
                gumballMachine.getTheStateName(),
                gumballMachine.getCount()
        );
    }
}

