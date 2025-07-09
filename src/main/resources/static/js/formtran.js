        // Scripts específicos do formulário
        function selectAccount(accountType) {
          document.querySelectorAll(".account-card").forEach((card) => {
            card.classList.remove("selected");
          });
          event.currentTarget.classList.add("selected");
        }

        function selectMovement(type, element) {
          document.querySelectorAll(".movement-btn").forEach((btn) => {
            btn.classList.remove("selected");
          });
          element.classList.add("selected");

          if (type === "debito") {
            document.getElementById("movDebito").checked = true;
          } else {
            document.getElementById("movCredito").checked = true;
          }
        }