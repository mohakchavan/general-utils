echo "Executing delete-cache.sh script..."

acquire_caches() {
  echo "Inside acquire_caches"

  curl --location --fail-with-body --request GET --header "Authorization: Bearer $token" \
    "https://api.github.com/repos/$repo/actions/caches" \
    --data "ref=$1" --data "per_page=$per_page" \
    --get --verbose --output resp.txt
}

delete_cache() {
  echo "Inside delete_cache"

  curl --location --fail-with-body --request DELETE --header "Authorization: Bearer $token" \
      "https://api.github.com/repos/$repo/actions/caches/$1" \
      --verbose
}

acquire_and_delete() {
  echo "Inside acquire_and_delete"

  acquire_caches "$1"

  local total=$(jq --raw-output ".total_count" resp.txt)

  if [ $total -gt 0 ]; then
    for cache_id in $(jq --raw-output ".actions_caches.[].id" resp.txt); do
      delete_cache "$cache_id"
    done
  fi

  if [ $total -gt $per_page ]; then
    echo "not completed"
    return 1
  else
    echo "completed"
    return 0
  fi
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
per_page=100

for br in "$@"; do

  echo "in for loop"
  completed=1
  while [ $completed -eq 1 ]; do
    if acquire_and_delete "$br" ; then
      completed=0
    else
      completed=1
    fi
  done

done
