name := "ai-space"

version := "0.1"

scalaVersion := "2.10.3"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0.RC1" % "test"

libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5" % "test"

javaHome := { 
  var s = System.getenv("JAVA_HOME") 
  if (s==null) {
     throw new RuntimeException( "No JDK found - try setting 'JAVA_HOME'." )
  } 
  val dir = new File(s)
  if (!dir.exists) { 
    throw new RuntimeException( "No JDK found - try setting 'JAVA_HOME'." )
  }  
  Some(dir) 
}

unmanagedJars in Compile <+= javaHome map { jh /*: Option[File]*/ =>
  val dir: File = jh.getOrElse(null)    // unSome
  val jfxJar = new File(dir, "/jre/lib/jfxrt.jar")
  if (!jfxJar.exists) {
    throw new RuntimeException( "JavaFX not detected (needs Java runtime 7u06 or later): "+ jfxJar.getPath )  // '.getPath' = full filename
  }
  Attributed.blank(jfxJar)
} 


