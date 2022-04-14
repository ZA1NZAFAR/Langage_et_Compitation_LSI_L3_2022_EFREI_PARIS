# Langage_et_Compitation_LSI_L3_2022_EFREI_PARIS
# Analyseur descendant

1) Contient un algorithme pour calculer Premier et Suivant pour chaque non-terminal d’une grammaire donnée.

2) Programme lit un ficheier contenant la grammaire, puis affiche le Premier et le Suivant pour chaque non-terminal.
 
3)	Calcule et affiche la table de l’analyse descendante à partir de fonctions Premier et Suivant.

# Syntax du fichier contenant la grammaire 
Le fichier contient l'ensemble des règles pour la grammaire.
Plusieurs règles peuvent être combinées en utilisant '|'.

## Quelques exemples des règles 
 - A -> BS
 - A -> sA
 - A -> s

 - A -> BS | sA | s

### Note: La décursification est détectée et traitée automatiquement



