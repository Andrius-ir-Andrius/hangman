export function getIdFromQuery(): null | string {
  const urlParams = new window.URLSearchParams(window.location.search);
  return urlParams.get("id");
}
