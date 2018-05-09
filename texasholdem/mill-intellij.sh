#!/bin/bash

echo 'Cleaning'
rm -rf out
rm -rf .idea

echo 'Generating IntelliJ project'
mill mill.scalalib.GenIdea/idea
