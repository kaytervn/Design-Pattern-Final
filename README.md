<h1 align=center>Strategy Pattern</h1>

<h2>1. Định nghĩa</h2>

Strategy Pattern là thuộc nhóm mẫu thiết kế hành vi (behavioral design patterns), nó cho phép lựa chọn giữa các thuật toán hoặc chiến lược thực thi tại thời điểm chạy của chương trình.

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

Mediator Pattern thuộc nhóm thiết kế hành vi (behavioral design pattern). Mục tiêu của nó là giảm sự phụ thuộc giữa các đối tượng bằng cách tạo ra một đối tượng trung gian (mediator) chịu trách nhiệm giao tiếp giữa các đối tượng khác nhau.

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
