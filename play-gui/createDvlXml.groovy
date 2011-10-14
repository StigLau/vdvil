def id = 0

new File("testfile.txt").eachLine {
        println """<part>
  <id>cfw$id</id>
  <dvl>
    <url>$it</url>
    <name>Click Flash Wirr</name>
  </dvl>  
  <start>${id*2}</start>  
  <end>${id*2+2}</end>
</part>"""
  id++
}