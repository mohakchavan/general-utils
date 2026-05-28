echo "Running create-checksum.sh script..."

checksums() {
  echo "Inside checksums"

  for file in $1; do
    echo "For $file file..."
    md5sum $file | cut --delimiter " " --fields 1 > $file.md5
    sha1sum $file | cut --delimiter " " --fields 1 > $file.sha1
    sha256sum $file | cut --delimiter " " --fields 1 > $file.sha256
    sha512sum $file | cut --delimiter " " --fields 1 > $file.sha512
  done
}

checksums "./target/*.jar"
checksums "./target/*.pom"

