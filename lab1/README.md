TQS - Tiago Carvalho -104142 - 20/02/2023

# **Lab 1 - Unit Testing (with JUnit 5)**

## **What's Unit Testing**?

**Unit Testing** is a type of software testing where individual units or components of a software are tested. The purpose is to validate that each unit of the software code performs as expected. Unit Testing is done during the development (coding phase) of an application by the developers. Unit Tests isolate a section of code and verify its correctness. A unit may be an individual function, method, procedure, module, or object.

</br>

## **Why should it be used?**

Unit tests help the developers to `(i)` understand the module contract (what to construct); `(ii)`
document the intended use of a component; `(iii)` prevent regression errors; `(iv)` increase confidence
in the code.

**If proper unit testing is done in early development, then it saves time and money in the end.**

</br>

## **What Not To Test**

- **Getters and setters**
- **Framework code**
  - specially generated code
- **Same conditions**
  - no point in having multiple tests for the same behavior or conditions
- **Complex behavior from collaborating objects**
  - that is not unit testing!

</br>

## **Test Phases**

1. **Setup**
   - `@BeforeEach`
2. **Exercise & Verify**
   - `@Test`
   - `Assert*X`*
3. **TearDown**
   - `@AfterEach`

</br>

## **Assertion** 

Here's a [quick list](https://howtodoinjava.com/junit5/junit-5-assertions-examples/) of assertions, most developers use when unit testing. The following list contains the following methods:

- `assertEquals()`
- `assertNotEquals()`
- `assertArrayEquals()`
- `assertIterableEquals()`
- `assertLinesMatch()`
- `assertNotNull()`
- `assertNull()`
- `assertNotSame()`
- `assertSame()`
- `assertTimeout()`
- `assertTimeoutPreemptively()`
- `assertTrue()`
- `assertFalse()`
- `assertThrows()`
- `fail()`

</br>

## **Code Coverage (with Jacoco)**

To check the code coverage of the tests made in this module, a Jacoco analysis was ran. Here's a [quick tutorial](https://www.baeldung.com/jacoco) on how to set up Jacoco and configure the testing environment.

These were the results of the tests. They can be check in the prints folder of the repository.

![Image1](https://github.com/tiagosora/TQS_104142/blob/main/lab1/prints/jacocoeuromillions.png)

![Image2](https://github.com/tiagosora/TQS_104142/blob/main/lab1/prints/jacocoboundedsetofnaturals.png)