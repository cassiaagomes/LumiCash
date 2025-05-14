#!/bin/bash

# Encontra todas as pastas (excluindo .git) e verifica se estão vazias
find . -type d -not -path '*/\.git/*' | while read dir; do
  # Conta quantos arquivos/pastas existem dentro da pasta
  count=$(find "$dir" -mindepth 1 -maxdepth 1 | wc -l)

  # Se estiver vazia, adiciona o .gitkeep
  if [ "$count" -eq 0 ]; then
    touch "$dir/.gitkeep"
    echo "Adicionado .gitkeep em $dir"
  fi
done
