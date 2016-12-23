## Tautology Verifier

This is a simple Scala based Tautology verification program. The problem statement is available on the PDF in the repo - "Tautology Verifier.pdf".

### Usage

To use the program start SBT and `compile` the code. After compilation, one can use the `run` command to pass a propositional statement to the program to verify. It accepts a valid propositional statement as argument and prints to console if the statement is a tautology or not. Example interactions from SBT console:

    $ sbt
    [info] Loading global plugins from /Users/bharadwaj/.sbt/0.13/plugins
    > compile
    [info] Compiling 1 Scala source to ...
    [success] Total time: 8 s, completed 28 Oct, 2016 3:47:04 AM
    
    > run "a & b & c & d & e & f & g & h & i"
    [info] Running in.bharathwrites.Tautology a & b & c & d & e & f & g & h & i
    a & b & c & d & e & f & g & h & i: NOT a tautology
    [success] Total time: 0 s, completed 28 Oct, 2016 3:59:19 AM
    
    > run "a | !a"
    [info] Running in.bharathwrites.Tautology a | !a
    a | !a: is a tautology
    [success] Total time: 0 s, completed 28 Oct, 2016 4:05:27 AM
    
    > test
    [info] Compiling 1 Scala source to ...
    [info] TautologySpec:
    [info] Verify Proposition
    [info] - should return false for non tautologies
    [info] - should return true for tautologies
    [info] Run completed in 181 milliseconds.
    [info] Total number of tests run: 2
    [info] Suites: completed 1, aborted 0
    [info] Tests: succeeded 2, failed 0, canceled 0, ignored 0, pending 0
    [info] All tests passed.
    [success] Total time: 1 s, completed 28 Oct, 2016 4:08:42 AM

### Design
The design has 4 pieces:

1. Program starts by identifying the number of variables in the statement and construct a truth table for variable count up to 10. The type of the truth table creation function is `List[Map[String, Boolean]]` Note: maybe a general functional solution to generate a 'n' dimensional truth table is possible. However such a function would not be simple. I attempted writing a general 'n' dimensional truth table creation function and on observing the complexity of the resultant code, discarded it. More so because the problem statement is clear that the variable count wont exceed 10. If this constraint were not present then I would have gone ahead with a more general function to generate a truth table
2. The second step is to iterate the rows of this truth table and evaluate if the statement evaluates to `true` for all rows of the truth table. Only if so is the statement a Tautology. The iteration happens in parallel (using parallel collections) and breaks out of the checking loop via an exception if even one of the rows evaluates to `false` 
3. The evaluation of a statement for a truth table row happens using Scala parser combinator. The inner `case class LogicParser` is a parser combinator - it exposes a single function called `sparse` which takes an expression and builds a parser using the logical parsing rules. If the statement is incorrect then the error is handled gracefully via the mechanisms of parser combinator error handling. The error is suppressed and not shown to the user (but it can be if that is needed). The logical parsing rules form the crux of the program
4. All the statements mentioned in the problem document are part of the tests. Tests use ScalaTest. I did ponder about writing a tautology generator via ScalaCheck to randomise the tests. But a random tautology generator is not a simple endevour and it might take me a couple of weeks to come up with an algo - hence did not go ahead. I hope this is sufficient! :)