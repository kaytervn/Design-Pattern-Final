package personal.command;

public class CommandPatternExample {
	public static void main(String[] args) {
		TV tv = new TV();
		RemoteControl remote = new RemoteControl();

		// Turn on the TV
		Command turnOnCommand = new TurnOnCommand(tv);
		remote.setCommand(turnOnCommand);
		remote.pressButton();

		// Turn off the TV
		Command turnOffCommand = new TurnOffCommand(tv);
		remote.setCommand(turnOffCommand);
		remote.pressButton();
	}
}