echo "Executing delete-cache script..."

print_branch() {
  echo "Inside print_branch $token"
  echo "Received $1"
}

if [ "$#" -eq 0 ]; then
  echo "Command line arguments required."
  exit 1

elif [ -z "$1" ]; then
    echo "GitHub token is required."
    exit 1

elif [ -z "$2" ]; then
    echo "Branch name is required."
    exit 1

fi

token="$1"
echo "$token"

shift 1

for br in "$@"; do
  print_branch "$br"
done
