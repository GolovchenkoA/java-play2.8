name := """play-java-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

// libraryDependencies ++=Seq(dep1, dep2)
libraryDependencies += guice