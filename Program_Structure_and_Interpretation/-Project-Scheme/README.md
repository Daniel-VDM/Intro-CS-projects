# -Project-Scheme
Final major project for Structure and Interpretation of Computer Programs.

************************************************
All code written by me was done in: scheme.py, scheme_reader.py, question.scm, test.scm
************************************************

Short Description of the project (Copied from the prompt)
-------------------------------------------------------------
In this project, you will develop an interpreter for a subset of the Scheme language. As you proceed, think about the issues that arise in the design of a programming language; many quirks of languages are byproducts of implementation decisions in interpreters and compilers. The subset of the language used in this project is described in the functional programming section of Composing Programs. Since we only include a subset of the language, your interpreter will not exactly match the behavior of other interpreters.

You will also implement some small programs in Scheme. Scheme is a simple but powerful functional language. You should find that much of what you have learned about Python transfers cleanly to Scheme as well as to other programming languages.

Short Description of each file:

- scheme.py: the Scheme evaluator
- scheme_reader.py: the Scheme syntactic analyzer
- questions.scm: a collection of functions written in Scheme
- tests.scm: a collection of test cases written in Scheme
- scheme_tokens.py: a tokenizer for Scheme
- scheme_primitives.py: definitions for primitive Scheme procedures
- buffer.py: a Buffer implementation, used in scheme_reader.py
- ucb.py: utility functions for 61A
- ok: the autograder
- tests: a directory of tests used by ok
- mytests.rst: A space for you to add your custom tests in Python; see section on adding your own tests

##### PERSONAL NOTE #####

Disclaimer:  All projects in CS61A (Structure and Interpretation of Computer Programs) at UC Berkeley were NOT done from scratch. Instead, students had to create core logic algorithms using the tools provided and learned. Furthermore, a testing program is provided alongside the project (called 'okpy') to ensure that the algorithms functions as desired. 
  
This project incorporated everything that I learned about Python to the application of building an interpreter for Scheme. Furthermore, it reinforce the my knowledge of Scheme. 

I was able to do the extra portion for this project which was to implement an optimized implementation of Scheme_eval (the interpreters evaluation routine) that supported tail recursion. I was also able to implement macros.



