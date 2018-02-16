lazy val baseSettings = Seq(
  name := "lagom-dojo",
  scalaVersion := "2.12.4"
)


lazy val step1 = project.in(file("step1")).settings(baseSettings)
