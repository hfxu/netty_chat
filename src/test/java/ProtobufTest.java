public class ProtobufTest {
    public static void main(String[] args) {
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder().setId(1234)
                .setName("Haifeng Xu")
                .setEmail("cataf@foxmail.com")
                .addPhone(AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setNumber("123-1234").setType(AddressBookProtos.Person.PhoneType.HOME)).build();
        System.out.println(person);
    }
}
