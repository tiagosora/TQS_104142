# **Lab 3.1 Employee manager example**

Teste e Qualidade Software - Tiago Carvalho - 104142 - 2023

</br>

### **a) Identify a couple of examples that use AssertJ expressive methods chaining.**

There're couple of evident examples of the `AssertJ` expression methods in the test files.

Example on [A_EmployeeRepositoryTest](https://github.com/tiagosora/TQS_104142/blob/main/lab3/lab3_1employee/src/test/java/tqsdemo/employeemngr/employee/A_EmployeeRepositoryTest.java):

``` java
assertThat(fromDb.getEmail()).isEqualTo( emp.getEmail());
```

Example on [B_EmployeeService_UnitTest](https://github.com/tiagosora/TQS_104142/blob/main/lab3/lab3_1employee/src/test/java/tqsdemo/employeemngr/employee/B_EmployeeService_UnitTest.java):

``` java
assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());
```

Example on [D_EmployeeRestControllerIT](https://github.com/tiagosora/TQS_104142/blob/main/lab3/lab3_1employee/src/test/java/tqsdemo/employeemngr/employee/D_EmployeeRestControllerIT.java):

``` java
assertThat(found).extracting(Employee::getName).containsOnly("bob");
```

Example on [E_EmployeeRestControllerTemplateIT](https://github.com/tiagosora/TQS_104142/blob/main/lab3/lab3_1employee/src/test/java/tqsdemo/employeemngr/employee/E_EmployeeRestControllerTemplateIT.java):

``` java
assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
```

</br>

### **b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).**

The the [B_EmployeeService_UnitTest](https://github.com/tiagosora/TQS_104142/blob/main/lab3/lab3_1employee/src/test/java/tqsdemo/employeemngr/employee/B_EmployeeService_UnitTest.java) file, the `employeeRepository` is initialized with the `@Mock` annotation

``` java
@Mock( lenient = true)
private EmployeeRepository employeeRepository;
```

After that, on the `setUp` function, initiated before each test, Mockito is used to mock the behaviour of the repository (and avoid involving a database)

``` java
Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
```

</br>

### **c) What is the difference between standard @Mock and @MockBean?**

Both `@Mock` and `@MockBean` annotations create mocked instances of a class or interface. `@Mock` can be used in every app structure, but `@MockBean` must be used on a spring application.

The annotation `@Mock` is designed to be used inside a test's class, in order to simulate the implementation of a class/interface, that is necessary to produce the intended tests. Using this annotation, the Mockito JUnit Jupiter will instantiate the mock and inject it in the test class. `@Mock` designed to be used alongside `@InjectMocks` in order to search for a suitable constructor to mock.

The annotation `@MockBean` creates and adds mock objects to the SpringBoot Application. It will replace any existing bean of the same type. If none of the same type is defined, a new one will be added.  The use `@MockBean` is very helpful during integration tests, where a bean should be mocked.

</br>

### **d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?**

The "[application-integrationtest.properties](https://github.com/tiagosora/TQS_104142/blob/main/lab3/lab3_1employee/src/main/resources/application.properties)" file has the properties to connect to a database and configure persistence.

Uncommenting the line `@TestPropertySource(locations = "application-integrationtest.properties")` on [D_EmployeeRestControllerIT](https://github.com/tiagosora/TQS_104142/blob/main/lab3/lab3_1employee/src/test/java/tqsdemo/employeemngr/employee/D_EmployeeRestControllerIT.java), during the test, it would be used the actual database where project data is stored. Using 

</br>

### **e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?**

Firstly, the test C doesn't envolve any database, unlikely D or E. The test C uses `@WebMvcTest`, which simulated the behavior of a application server, and uses `MockMvc` that provides an expressive API. This test also simulates dependencies related to the service implementation with the `@MockBean` annotation. Here, the repository component is also not involved.

Both test D and E are integration tests involving multiple components. Both involve the service implementation, the repository, and the database components. The tests start the full Web context through the `@SpringBootTest` annotation, and the API is implemented in SpringBoot. The difference between these two tests is that in this implemented API, test D uses `MockMvc` as the entry point for Spring MVC server-side test support. Test E uses `TestRestTemplate`, a REST client to create realistic requests, involving responses as well.
