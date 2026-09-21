// Väntar tills hela HTML sidan har laddats klart
document.addEventListener("DOMContentLoaded", function () {

  // Hämtar alla länkar som har attributet data confirm
  const confirmLinks = document.querySelectorAll("[data-confirm]");

  // Loppar igenom alla länkar
  confirmLinks.forEach(function (link) {

    // Lyssnar efter klick på länk
    link.addEventListener("click", function (event) {

      // Hämtar meddelandet från data confrim attributet
      const message = link.dataset.confirm;

      // Visar bekräftelseruta innan åtgärden utförs
      if (!window.confirm(message)) {

        // Stoppar länken om användaren trycket på Avbryt
        event.preventDefault();
      }
    });
  });
});