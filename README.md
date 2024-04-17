<h1 align=center>Strategy Pattern</h1>

<h2>1. Định nghĩa</h2>

Strategy Pattern thuộc nhóm mẫu thiết kế hành vi (behavioral design patterns), nó cho phép lựa chọn giữa các thuật toán hoặc chiến lược thực thi tại thời điểm chạy của chương trình.

Các thành phần:

- **Context:** Là đối tượng chứa các thuộc tính cần xử lý và sử dụng một chiến lược cụ thể.
- **Strategy Interface:** Là interface chứa phương thức để thực thi chiến lược.
- **Concrete Strategies:** Các lớp cụ thể triển khai các phương thức của Strategy Interface.

<h2>2. Cài đặt</h2>

**2.1 Context (Transportation)**

Xây dựng chương trình có lớp Transportation chứa danh sách các hàng hóa Parcel cần được vận chuyển và sử dụng một chiến lược cụ thể để thực hiện việc vận chuyển.

```java
public class Transportation {
	private List<Parcel> parcels;
	private TransportStrategy transportStrategy;

	public Transportation() {
		this.parcels = new ArrayList<>();
	}

	public void addParcel(Parcel parcel) {
		parcels.add(parcel);
	}

	public void transport() {
		int weight = 0;
		for (Parcel parcel : parcels) {
			weight += parcel.getWeight();
		}
		transportStrategy.transport(weight);
	}

	public void setTransportStrategy(TransportStrategy transportStrategy) {
		this.transportStrategy = transportStrategy;
	}
}
```

Lớp Parcel đại diện cho mỗi hàng hóa cần được vận chuyển, chứa thông tin về tên và trọng lượng của hàng hóa.

```java
public class Parcel {
	private String name;
	private int weight;

	public Parcel(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}
}
```

**2.2 Strategy Interface (TransportStrategy)**

TransportStrategy là một giao diện định nghĩa phương thức transport(int mass). Giao diện này đại diện cho các thuật toán vận chuyển khác nhau.

```java
public interface TransportStrategy {
	void transport(int mass);
}
```

**2.3 Concrete Strategies (TruckStrategy, MotobikeStrategy, và CarStrategy)**

TruckStrategy, MotobikeStrategy, và CarStrategy là các lớp thực thi TransportStrategy. Mỗi lớp cài đặt phương thức transport(int mass) một cách khác nhau để xử lý việc vận chuyển hàng hóa dựa trên khối lượng của chúng.

```java
public class TruckStrategy implements TransportStrategy {

	@Override
	public void transport(int mass) {
		if (mass <= 2000) {
			System.out.println("Parcels with a mass of " + mass + " kg are transporting by truck.");
		} else {
			System.out.println("Parcels with a mass of " + mass + " kg are OVERWEIGHT for transportation by truck.");
		}
		System.out.println("---");
	}
}
```

```java
public class MotobikeStrategy implements TransportStrategy {

	@Override
	public void transport(int mass) {
		if (mass <= 50) {
			System.out.println("Parcels with a mass of " + mass + " kg are transporting by motobike.");
		} else {
			System.out.println("Parcels with a mass of " + mass + " kg are OVERWEIGHT for transportation by motobike.");
		}
		System.out.println("---");
	};
}
```

```java
public class CarStrategy implements TransportStrategy {

	@Override
	public void transport(int mass) {
		if (mass <= 350) {
			System.out.println("Parcels with a mass of " + mass + " kg are transporting by car.");
		} else {
			System.out.println("Parcels with a mass of " + mass + " kg are OVERWEIGHT for transportation by car.");
		}
		System.out.println("---");
	}
}
```

<h2>3. Thử nghiệm</h2>

Trong hàm main, ta tạo một đối tượng Transportation và thêm các hàng hóa vào danh sách. Sau đó, ta lần lượt thiết lập các chiến lược vận chuyển và gọi phương thức transport() để thực hiện vận chuyển.

```java
public class Main {
	public static void main(String[] args) {
		Transportation trans = new Transportation();

		trans.addParcel(new Parcel("Laptop", 3));
		trans.addParcel(new Parcel("TV", 20));
		trans.addParcel(new Parcel("Sofa", 40));
		trans.addParcel(new Parcel("Fridge", 100));
		trans.addParcel(new Parcel("Washing Machine", 80));

		trans.setTransportStrategy(new MotobikeStrategy());
		trans.transport();

		trans.setTransportStrategy(new CarStrategy());
		trans.transport();

		trans.setTransportStrategy(new TruckStrategy());
		trans.transport();
	}
}
```

Output:

```
Parcels with a mass of 243 kg are OVERWEIGHT for transportation by motobike.
---
Parcels with a mass of 243 kg are transporting by car.
---
Parcels with a mass of 243 kg are transporting by truck.
---
```

<h1 align=center>Mediator Pattern</h1>

<h2>1. Định nghĩa</h2>

Mediator Pattern thuộc nhóm mẫu thiết kế hành vi (behavioral design pattern). Mục tiêu của nó là giảm sự phụ thuộc giữa các đối tượng bằng cách tạo ra một đối tượng trung gian (mediator) chịu trách nhiệm giao tiếp giữa các đối tượng khác nhau.

Các thành phần:

- **Mediator:** Interface hoặc lớp trừu tượng chứa các phương thức để giao tiếp với các thành viên (đối tượng).
- **Concrete Mediator:** Lớp triển khai interface Mediator và chịu trách nhiệm quản lý các thành viên và giao tiếp giữa chúng.
- **Object:** Các đối tượng tham gia giao tiếp thông qua trung gian.
- **Concrete Object:** Lớp triển khai interface hoặc lớp cơ sở Object.

<h2>2. Cài đặt</h2>

**2.1 Mediator Interface (Mediator)**

Xây dựng chương trình có Mediator là một giao diện định nghĩa hai phương thức: giveTalk(String content, Member member) để truyền thông điệp từ một thành viên (member) tới tất cả các thành viên khác, và addMember(Member member) để thêm một thành viên mới vào danh sách.

```java
public interface Mediator {
	void giveTalk(String content, Member member);

	void addMember(Member member);
}
```

**2.2 Concrete Mediator (AppointmentMediator)**

AppointmentMediator là một lớp cài đặt giao diện Mediator. Nó chứa một danh sách các thành viên (members) và triển khai các phương thức giveTalk(String content, Member member) và addMember(Member member).

```java
public class AppointmentMediator implements Mediator {

	public AppointmentMediator(String topic) {
		System.out.println("The topic " + topic + " is being brought up");
	}

	List<Member> members = new ArrayList<>();;

	@Override
	public void giveTalk(String content, Member member) {
		for (Member mem : this.members) {
			if (!mem.equals(member)) {
				mem.hear();
			}
		}
	}

	@Override
	public void addMember(Member member) {
		System.out.println(member.name + " joined this appointment");
		this.members.add(member);
	}
}
```

**2.3 Object (Member) và Concrete Object (MemberImpl)**

Member là một lớp trừu tượng đại diện cho một thành viên tham gia cuộc họp. Nó chứa một tham chiếu tới đối tượng AppointmentMediator (mediator).

```java
public abstract class Member {
	protected AppointmentMediator mediator;
	protected String name;
	protected String role;

	public Member(AppointmentMediator med, String name, String role) {
		this.mediator = med;
		this.name = name;
		this.role = role;
	}

	public abstract void talk(String content);

	public abstract void hear();
}
```

MemberImpl là một lớp cài đặt của Member. Nó triển khai các phương thức talk(String content) và hear() và hiển thị thông điệp của thành viên khi nó nói chuyện hoặc nghe.

```java
public class MemberImpl extends Member {

	public MemberImpl(AppointmentMediator med, String name, String role) {
		super(med, name, role);
	}

	@Override
	public void talk(String content) {
		System.out.println("---");
		System.out.println(this.name + " is talking: " + content);
		this.mediator.giveTalk(content, this);
	}

	@Override
	public void hear() {
		System.out.println(this.name + " (" + this.role + ") " + "is hearing");
	}
}
```

<h2>3. Thử nghiệm</h2>

Trong lớp MediatorDemo, ta tạo một đối tượng AppointmentMediator với một chủ đề cụ thể. Sau đó, tạo các đối tượng MemberImpl và thêm các thành viên đó vào AppointmentMediator. Cuối cùng, gọi phương thức talk và các thành viên sẽ giao tiếp thông quan trung gian (AppointmentMediator).

```java
public class MediatorDemo {
	public static void main(String[] args) {
		AppointmentMediator mediator = new AppointmentMediator("Employee Wellness Initiatives");

		Member john = new MemberImpl(mediator, "John", "CEO");
		Member sarah = new MemberImpl(mediator, "Sarah", "HR Manager");
		Member michael = new MemberImpl(mediator, "Michael", "Operations Supervisor");

		mediator.addMember(john);
		mediator.addMember(sarah);
		mediator.addMember(michael);

		john.talk("Today, we'll discuss our employee wellness initiatives.");
		sarah.talk("Implement flexible work arrangements to promote work-life balance.");
		michael.talk("Provide gym membership discounts to encourage physical fitness.");
		john.talk("Thank you, everyone, for your valuable suggestions.");
	}
}
```

Output:

```
The topic Employee Wellness Initiatives is being brought up
John joined this appointment
Sarah joined this appointment
Michael joined this appointment
---
John is talking: Today, we'll discuss our employee wellness initiatives.
Sarah (HR Manager) is hearing
Michael (Operations Supervisor) is hearing
---
Sarah is talking: Implement flexible work arrangements to promote work-life balance.
John (CEO) is hearing
Michael (Operations Supervisor) is hearing
---
Michael is talking: Provide gym membership discounts to encourage physical fitness.
John (CEO) is hearing
Sarah (HR Manager) is hearing
---
John is talking: Thank you, everyone, for your valuable suggestions.
Sarah (HR Manager) is hearing
Michael (Operations Supervisor) is hearing
```

<h1 align=center>State Pattern</h1>

<h2>1. Định nghĩa</h2>

State Pattern thuộc nhóm mẫu thiết kế hành vi (behavioral design pattern), nó cho phép một đối tượng thay đổi hành vi của mình khi trạng thái nội bộ của đối tượng đó thay đổi.

Các thành phần:

- **Context:** Đối tượng chứa trạng thái hiện tại của nó và gọi các phương thức của trạng thái đó.
- **State Interface:** Interface chứa các phương thức mà mỗi trạng thái cụ thể cần triển khai.
- **Concrete States:** Các lớp cụ thể triển khai State Interface và định nghĩa hành vi cho mỗi trạng thái.

<h2>2. Cài đặt</h2>

**2.1 Context (Light)**

Xây dựng chương trình có lớp Light đại diện cho đối tượng đèn. Nó chứa một trạng thái nội bộ (state) và triển khai hai phương thức turnOn() và turnOff() để thực hiện hành động tương ứng.

```java
public class Light {
    private LightState state;

    public Light() {
        state = new OffState();
    }

    public void setState(LightState state) {
        this.state = state;
    }

    public void turnOn() {
        state.turnOn();
        setState(new OnState());
    }

    public void turnOff() {
        state.turnOff();
        setState(new OffState());
    }
}
```

**2.2 State Interface (LightState)**

LightState là một giao diện định nghĩa hai phương thức: turnOn() để bật đèn và turnOff() để tắt đèn.

```java
interface LightState {
    void turnOn();

    void turnOff();
}
```

**2.3 Các Concrete States (OnState và OffState)**

OnState và OffState là hai lớp cài đặt giao diện LightState. Mỗi lớp đại diện cho một trạng thái của đèn.

```java
public class OnState implements LightState {
    @Override
    public void turnOn() {
        System.out.println("The light has already been turned on.");
    }

    @Override
    public void turnOff() {
    	System.out.println("The light is turned off.");
    }
}
```

```java
public class OffState implements LightState {
    @Override
    public void turnOn() {
        System.out.println("The light is turned on.");
    }

    @Override
    public void turnOff() {
    	System.out.println("The light has already been turned off.");
    }
}
```

<h2>3. Thử nghiệm</h2>

Trong lớp Main, chúng ta tạo một đối tượng Light và sử dụng các phương thức turnOn() và turnOff() để thực hiện các hành động tương ứng trên đèn. Kết quả sẽ được in ra màn hình, hiển thị thông báo và trạng thái của đèn sau mỗi hành động.

```java
public class Main {
    public static void main(String[] args) {
        Light light = new Light();

        light.turnOn();
        light.turnOn();
        light.turnOff();
        light.turnOff();
    }
}
```

Output:

```
The light is turned on.
The light has already been turned on.
The light is turned off.
The light has already been turned off.
```

<h1 align=center>Template Pattern</h1>

<h2>1. Định nghĩa</h2>

Template Pattern thuộc nhóm mẫu thiết kế hành vi (behavioral design pattern) cho phép xác định một bộ khung (template) cho một thuật toán trong một lớp cơ sở và để các lớp con triển khai các bước cụ thể của thuật toán mà không thay đổi cấu trúc tổng thể của nó.

Các thành phần:

- **Abstract Template Class:** Lớp cơ sở chứa các phương thức cốt lõi và một phương thức template để gọi các phương thức cốt lõi đó.
- **Concrete Classes:** Các lớp con triển khai các phương thức cốt lõi để thực hiện các bước cụ thể của thuật toán.

<h2>2. Cài đặt</h2>

**2.1 Abstract Template Class (RecipeTemplate)**

Xây dựng chương trình có lớp RecipeTemplate là một lớp trừu tượng định nghĩa một bản mẫu (template) chứa các phương thức cốt lõi để chuẩn bị, nấu và dọn dẹp cho một công thức nấu ăn.

```java
public abstract class RecipeTemplate {
	public void prepareIngredients() {
		System.out.println("Preparing ingredients");
	}

    public abstract void cook();

    public void cleanUp() {
    	System.out.println("Cleaning up");
    }

    public final void makeRecipe() {
    	prepareIngredients();
    	cook();
    	cleanUp();
    }

}
```

**2.2 Các Concrete Classes (PizzaRecipe và SaladRecipe)**

PizzaRecipe và SaladRecipe là hai lớp con kế thừa từ RecipeTemplate. Mỗi lớp con triển khai phương thức cook() để xác định cách nấu ăn cụ thể cho công thức của nó.

```java
public class PizzaRecipe extends RecipeTemplate {
	@Override
	public void cook() {
		System.out.println("Baking the pizza at 400 degrees F");
	}
}
```

```java
public class SaladRecipe extends RecipeTemplate {
	@Override
	public void cook() {
		System.out.println("Tossing the salad ingredients");
	}
}
```

<h2>3. Thử nghiệm</h2>

Trong hàm main, ta tạo các đối tượng của các lớp cụ thể và gọi phương thức makeRecipe() để thực hiện các bước chuẩn bị, nấu và dọn dẹp cho mỗi loại món.

```java
public class Main {
	public static void main(String[] args) {
		System.out.println("Making a pizza:");
		RecipeTemplate pizzaRecipe = new PizzaRecipe();
		pizzaRecipe.makeRecipe();

		System.out.println("---");

		System.out.println("Making a salad:");
		RecipeTemplate saladRecipe = new SaladRecipe();
		saladRecipe.makeRecipe();
	}
}
```

Output:

```
Making a pizza:
Preparing ingredients
Baking the pizza at 400 degrees F
Cleaning up
---
Making a salad:
Preparing ingredients
Tossing the salad ingredients
Cleaning up
```

<h1>Chain Of Responsibility Pattern</h1>

<h2>1. Định nghĩa</h2>

Chain Of Responsibility Pattern thuộc nhóm mẫu thiết kế hành vi (behavioral design pattern), nó cho phép các đối tượng được xử lý tuần tự theo một chuỗi và không biết về đối tượng xử lý tiếp theo trong chuỗi.

Các thành phần:

- Handler: Interface hoặc lớp trừu tượng chứa phương thức để xử lý yêu cầu và có một tham chiếu đến handler tiếp theo trong chuỗi.
- Concrete Handlers: Các lớp cụ thể triển khai phương thức xử lý yêu cầu và quyết định liệu họ có thể xử lý yêu cầu đó hay không.

<h2>2. Cài đặt</h2>

**2.1 Handler (TransportHandler)**

TransportHandler là lớp cơ sở chứa phương thức handleDepart() để xử lý yêu cầu vận chuyển và quyết định liệu nó có thể xử lý yêu cầu đó hay không. Nếu không thể xử lý, nó sẽ chuyển yêu cầu đó cho handler tiếp theo trong chuỗi.

```java
public abstract class TransportHandler {

	protected TransportHandler nextHandler;

	public void handleDepart(TravelRequest request) {
		System.out.println("Checking for transportation: " + this.getClass().getSimpleName());
		if (this.isAcceptable(request.getPassengers())) {
			this.depart(request);
		} else if (nextHandler != null) {
			System.out.println("Request is rejected.");
			nextHandler.handleDepart(request);
		}
	}

	public void setNext(TransportHandler handler) {
		this.nextHandler = handler;
	}

	protected abstract boolean isAcceptable(int passengers);

	protected abstract void depart(TravelRequest request);
}
```

**2.2 Các Concrete Handlers (Car, Bus và Airplane)**

Car, Bus, Airplane: Các lớp này triển khai phương thức xử lý để kiểm tra xem số lượng hành khách có phù hợp với loại phương tiện của họ hay không và thực hiện vận chuyển tương ứng.

```java
public class Car extends TransportHandler {

	private static final int MAX_SIZE = 6;

	@Override
	protected boolean isAcceptable(int passengers) {
		return passengers <= MAX_SIZE;
	}

	@Override
	protected void depart(TravelRequest request) {
		System.out.println("The car is departing with a load of " + request.getPassengers() + " passengers.");
		System.out.println("---");
	}
}
```

```java
public class Bus extends TransportHandler {

	private static final int MIN_SIZE = 7;
	private static final int MAX_SIZE = 30;

	@Override
	protected boolean isAcceptable(int passengers) {
		return passengers >= MIN_SIZE && passengers <= MAX_SIZE;
	}

	@Override
	protected void depart(TravelRequest request) {
		System.out.println("The bus is departing with a load of " + request.getPassengers() + " passengers.");
		System.out.println("---");
	}
}
```

```java
public class Airplane extends TransportHandler {

	private static final int MIN_SIZE = 31;

	@Override
	protected boolean isAcceptable(int passengers) {
		return passengers >= MIN_SIZE;
	}

	@Override
	protected void depart(TravelRequest request) {
		System.out.println("The airplane is departing with a load of " + request.getPassengers() + " passengers.");
		System.out.println("---");
	}
}
```

**2.3 Lớp TourGuide**

TourGuide là một lớp trung gian để tạo ra chuỗi xử lý vận chuyển. Nó khởi tạo các đối tượng Car, Bus và Airplane, sau đó thiết lập chuỗi bằng cách gọi setNext() theo thứ tự từ Car đến Bus, rồi từ Bus đến Airplane. Cuối cùng, nó trả về đối tượng Car để bắt đầu quá trình xử lý yêu cầu.

```java
public class TourGuide {

	public static TransportHandler getTransport() {
		TransportHandler car = new Car();
		TransportHandler bus = new Bus();
		TransportHandler airplane = new Airplane();

		car.setNext(bus);
		bus.setNext(airplane);
		return car;
	}
}
```

<h2>3. Thử nghiệm</h2>

Trong lớp Main, chúng ta sử dụng TourGuide.getTransport() để lấy đối tượng xử lý đầu tiên trong chuỗi xử lý vận chuyển. Sau đó, chúng ta gọi phương thức handleDepart() trên đối tượng xử lý với các yêu cầu vận chuyển khác nhau (TravelRequest). Kết quả sẽ được in ra màn hình, hiển thị thông báo về quá trình xử lý vận chuyển.

```java
public class Main {

	public static void main(String[] args) {
		TourGuide.getTransport().handleDepart(new TravelRequest(5));
		TourGuide.getTransport().handleDepart(new TravelRequest(10));
		TourGuide.getTransport().handleDepart(new TravelRequest(97));
	}
}
```

Output:

```
Checking for transportation: Car
The car is departing with a load of 5 passengers.
---
Checking for transportation: Car
Request is rejected.
Checking for transportation: Bus
The bus is departing with a load of 10 passengers.
---
Checking for transportation: Car
Request is rejected.
Checking for transportation: Bus
Request is rejected.
Checking for transportation: Airplane
The airplane is departing with a load of 97 passengers.
---
```
