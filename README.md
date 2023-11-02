# ParallelAlgorithms
By: Jason Castillo (jzc248) and Carie Navio (cen847)

Built using IntelliJ IDEA 2023.2.3 (Ultimate Edition) 

*Testing completed using JUNIT*

## How to run:
1. Open Homework3 as a project in IntelliJ IDEA
2. Go to Tester.java
3. Right click on Tester.java file to run all tests or hover over each test case to run individually

Information for each test case is noted above the test case

### Challenges:
During the first run of Prefix sum it displayed the correct value, and we moved on. 
After running it at a later time, the value changed and the answer was nondeterministic. 
After some troubleshooting it was determined there was a thread synchronization issue where threads were reading before
another thread had a chance to write. Added Wait() and notify() methods to resolve the issue. 

Pls be gentle >.<
