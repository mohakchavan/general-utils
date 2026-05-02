echo "Executing delete-cache.sh script..."

print_branch() {
  echo "Inside print_branch"
  echo "Received $1"

  curl --location --request GET --header "Authorization: Bearer $token" \
    "https://api.github.com/repos/$repo/actions/caches" --data "ref=$1" --get --verbose
}

if [ "$#" -eq 0 ]; then
  echo "Command line arguments required."
  exit 1

elif [ -z "$1" ]; then
    echo "GitHub repository is required."
    exit 1

elif [ -z "$2" ]; then
    echo "GitHub token is required."
    exit 1

elif [ -z "$3" ]; then
    echo "Branch name is required."
    exit 1

fi

repo="$1"
shift 1
token="$1"
shift 1

for br in "$@"; do
  print_branch "$br"
done
