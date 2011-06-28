import org.junit.Test
import org.junit.Assert._

class XPathFilterTest {
  @Test def htmlFilter {
    assertEquals("http://images.com/testImage.jpg", (exampleHtml \\ "a" \\ "@src").text)
  }

  val exampleHtml = <html xmlns="http://www.w3.org/ns/SMIL" version="3.0" baseProfile="Language">
    <head>
      <layout>
          <region xml:id="source" height="50%"/>
          <region xml:id="destination" top="50%"/>
      </layout>
    </head>
    <body>
      <a href="embeddedSMIL.smil" target="destination" accesskey="a">
          <img region="source" src="http://images.com/testImage.jpg" dur="indefinite"/>
      </a>
    </body>
    <jalla src="this should not be found"/>
  </html>
}