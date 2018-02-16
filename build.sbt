lazy val baseSettings = Seq(
  name := "lagom-dojo",
  scalaVersion := "2.12.4"
)

lazy val step1 = project.in(file("step1")).settings(baseSettings)
lazy val step2 = project.in(file("step2")).settings(baseSettings)

lazy val `lagom-dojo` = project.in(file(".")).settings(baseSettings)
  .aggregate(step1, step2)