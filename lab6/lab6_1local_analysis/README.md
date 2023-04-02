# **Lab6_1 - Local Analysis**

</br>

## Running SonarQube Server

```bash
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

Once your instance is up and running, Log in to [http://localhost:9000](http://localhost:9000/) and change the default admin authentication password.

</br>

## Test Results

Upon preforming the following command:

```bash
mvn clean verify sonar:sonar -Dsonar.projectKey=lab6_1local_analysis -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_faaa68ce5b43ec5f1e8b613bb52f4bc4f66b8498
```

My project successfully passed the defined quality gate, with a total of 0 bugs, 0 vulnerabilities and 1 Security Hotspot

| Issue              | Problem description                                          | How to solve                                                 |
| ------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Security Hotspot   | Weak Cryptography in [Dip.java](https://github.com/tiagosora/TQS_104142/blob/main/lab6/lab6_1local_analysis/src/main/java/tqs/euromillions/Dip.java). the use of `new Random()` is not secured. | Make sure that using this pseudorandom number generator is safe here. |
| Code Smell (Major) | Refactor the code in order to not assign to this loop counter from within the loop body. | Increment the loop counter on the definition of the for loop. |
| Code Smell (Major) | Invoke method(s) only conditionally.                         | Implement the necessary condition.                           |
| Code Smell (Major) | The program directly writes to the standard outputs.         | Replace this use of System.out or System.err by a logger.log. |

