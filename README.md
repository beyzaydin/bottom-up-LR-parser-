# bottom-up-LR-parser-
<h2>Bottom up LR Parser</h2> 

How to run the code?
<br>
>java LrParser.java (input) (output file name)

Example input 
* >java LrParser.java id+id*id$ output.txt
* >Stack     Input Action<Br>
  0 id + id * id $ Shift 5<br>
  0id5 + id * id $ Reduce 6<br>
  0F3 + id * id $ Reduce 4<br>
  0T2 + id * id $ Reduce 2<br>
  0E1 + id * id $ Shift 6<br>
  0E1+6 id * id $ Shift 5<br>
  0E1+6id5 * id $ Reduce 6<br>
  0E1+6F3 * id $ Reduce 4<br>
  0E1+6T9 * id $ Shift 7<br>
  0E1+6T9*7 id $ Shift 5<br>
  0E1+6T9*7id5 $ Reduce 6<br>
  0E1+6T9*7F10 $ Reduce 3<br>
  0E1+6T9 $ Reduce 1<br>
  0E1 $ Accept<br>
