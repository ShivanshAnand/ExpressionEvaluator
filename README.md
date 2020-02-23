# ExpressionEvaluator by Shivansh Anand
This repo contains an arithmetic expression evaluator written in Java from scratch (without using any external libraries).
Following steps are followed to evaluate the expression :
  1. Check for balanced paranthesis.
  2. Tokenize the expression into operands, operators, brackers etc.
  3. Convert the infix(human readable) expression to the postfix(computer friendly and easy to implement) form. This was done        using Dijkstra's Shunting Yard algorithm. (more explanation here : https://en.wikipedia.org/wiki/Shunting-yard_algorithm)
  4. Evaluate the postfix expression. (more explanation here : https://www.geeksforgeeks.org/stack-set-4-evaluation-postfix-        expression/)
  
