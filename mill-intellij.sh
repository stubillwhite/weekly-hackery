#!/bin/bash

echo 'Cleaning'
rm -rf out
rm -rf .idea
rm -rf .idea_modules/

echo 'Generating IntelliJ project'
mill mill.scalalib.GenIdea/idea
