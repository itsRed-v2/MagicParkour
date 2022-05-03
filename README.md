# MagicParkour

## Configuration d'un parkour

La création d'un parkour se fait par son écriture dans le fichier `parkour.json`

***

## Types de valeurs

Tout au long de la configuration du parkour, vous aurez besoin de renseigner des valeurs telles que des vecteurs (coordonnées), des blocks, etc.

Voici les différents types de valeurs utilisées:

### Vecteurs

Les vecteurs sont des tableau de 3 nombres. Par exemple: `[2, 7, 8]` représente un vecteur qui a les valeurs x: 2, y: 7 et z: 8.

### Coordonnées

Les clés `"pos"` attendent toujours une coordonnée.

Les coordonnées sont des vecteurs qui représentent une position absolue dans le monde. Par exemple: `"pos": [237, 86, 76]` représente les coordonnées d'un bloc positionné en x: 237, y: 86 et z: 76

### BlockData

Les clés `"block"` attendent toujours un BlockData. Cependant d'autres clés peuvent aussi attendre ce genre de valeurs.

Cette valeur se présente sous la forme d'un string représentant un block. Par exemple: `"stone"`, `"minecraft:white_stained_glass"`, ou encore `"ladder[facing=east]"` sont des BlockData valides.

Vous l'avez peut-être remarqué, c'est la même forme qui est utilisée en vanilla avec les commande `/setblock` ou `/fill`

Malheureusement le plugin ne supporte pas d'associer des NBT aux tile entities. Cela signifie qu'on ne peut pas renseigner un panneau avec du texte ou un coffre contenant des items, comme on peut le faire en vanilla avec par exemple `oak_sign{Text1:'{"text":"Line1"}'}`

### Types primitifs

Vous aurez aussi parfois besoin de renseigner des types primitifs json, comme des strings ou des booléens.

***

## Parkour.json

Le fichier `parkour.json` est composé d'une première section contenant les différents parkours. Chaque clé est l'id d'un parkour, la sous-section qui lui est associée contient toutes les données du parkour.

Voici par exemple un fichier qui contient 3 parkours ayant pour id respectivement `lobby`, `parkour2` et `parkour3`. Pour plus de lisibilité, les données des parkours ont été omises.

```json
{
  "lobby": {},
  "parkour2": {},
  "parkour3": {}
}
```

***

## Définir un parkour

Pour être valide, un parkour doit nécéssairement contenir certains attributs, mais certains sont optionnels pour plus de personnalisation.

Voici un exemple simple de parkour:
```json
{
  "lobby": {
    "world": "world",
    "start": {
      "pos": [231, 85, 79]
    },
    "steps": [
      {
        "pos": [231, 86, 82]
      },
      {
        "type": "obstacle",
        "pos": [230, 88, 85],
        "blocks": [
          { "offset": [0, 0, 0] }
        ]
      },
      {
        "pos": [230, 86, 86]
      }
    ],
    "end": {
      "pos": [228, 87, 84]
    }
  }
}
```

## Éléments requis

Voici une liste des éléménts nécéssaires pour qu'un parkour soit valide.
Si un seul de ces éléments manque ou n'est pas valide, le parkour ne se chargera pas et une erreur sera envoyée dans la console.

### "world"

`"world"` correspond à l'id du monde dans lequel le parkour se trouve.

### "start"

Cette section contient les données du point de départ du parkour.

Il est nécéssaire d'y renseigner l'attribut `"pos"` qui précisera les [coordonnées](#coordonnées) du point de départ.

On peut aussi y renseigner l'attribut `"block"` pour définir le [block](#blockdata) qui sera utilisé comme point de départ. La valeur par défaut est `"gold_block"`

Voici un exemple de section `"start"`:

```json
"start": {
  "pos": [235, 85, 74],
  "block": "gold_ore"
}
```

### "end"

Cette section est similaire à la section `"start"`, sauf qu'elle définit **le dernier block** du parkour. La valeur par défaut de `"block"` pour cette section est `"diamond_block"`

### "steps"

`"steps"` a pour valeur un tableau d'éléments représentant chacun un *step* (étape) du parkour. Les steps les plus simples prennent souvent la forme de simples blocs.

- Ce tableau est souvent la partie la plus longue du fichier, car elle définit chaque step un par un.

- Pour terminer le parkour, le joueur parcourir tous les steps dans l'ordre puis arriver sur le block défini dans `"end"`.

Il existe plusieurs [types de steps](#types-de-steps) ayant chacun des propriétés et des effets différents.

## Éléments optionnels

### "customName"

Cet attribut est un string qui définit le nom qui sera affiché lorsqu'un joueur commence le parkour. Il peut contenir des codes couleurs ou de style tels que `§e`, `§l`. Si aucun code couleur n'est spécifié, le nom s'affichera en orange (code couleur `§6`). Voici une [liste des codes couleurs](https://minecraft.tools/fr/color-code.php) que j'utilise souvent quand j'ai un trou de mémoire.

Par exemple
```json
"customName": "§a§lSky line"
```
fera apparaître le message "Début du parkour **Sky line**" (avec "Sky line" en vert et en gras) quand un joueur commencera le parkour.

***

## Types de steps

