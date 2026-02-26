#!/usr/bin/env bash
#
# Publish the relocation POM to Maven Central.
#
# This uploads a POM-only artifact at the old coordinates
# (com.github.pernyfelt.sieparser:SieParser) with a <relocation>
# element pointing to the new coordinates (se.alipsa:SieParser).
#
# Prerequisites:
#   - GPG signing key available to maven-gpg-plugin
#   - Sonatype Central credentials in ~/.m2/settings.xml:
#     <server>
#       <id>central</id>
#       <username>TOKEN_USERNAME</username>
#       <password>TOKEN_PASSWORD</password>
#     </server>
#
# Usage:
#   cd relocation && ./release.sh
#

set -euo pipefail

cd "$(dirname "$0")"

echo "Publishing relocation POM (com.github.pernyfelt.sieparser:SieParser -> se.alipsa:SieParser)..."
echo ""

mvn deploy

echo ""
echo "Relocation POM published successfully."
