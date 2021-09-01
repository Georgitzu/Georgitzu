package georgitzu.ruby.crystal;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item.Settings;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class RubyCrystal implements ModInitializer {

	public static final Item RUBY_CRYSTAL = new Item(new Settings().group(ItemGroup.MISC));

	public static final Block RUBY_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).strength(5.0F, 5.0F).sounds(BlockSoundGroup.METAL).breakByTool(FabricToolTags.PICKAXES));

	public static final Item POWERFUL_RUBY_CRYSTAL = new Item(new Item.Settings().group(ItemGroup.FOOD).food(RubyCrystalFood.POWERFUL_RUBY_CRYSTAL));
	
	public static final Block RUBY_ORE = new RubyOreBlock(FabricBlockSettings.copy(Blocks.STONE));
	
	private static ConfiguredFeature<?, ?> RUBY_ORE_OVERWORLD = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, RUBY_ORE.getDefaultState(), 8))
	.decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(0, 0, 10))).spreadHorizontally().repeat(2);

	public static final ArmorMaterial RUBY_ARMOR = new RubyArmorMaterial();

	@Override
	public void onInitialize() {

	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_crystal"), RUBY_CRYSTAL);
	Registry.register(Registry.BLOCK, new Identifier("rubycrystal", "ruby_block"), RUBY_BLOCK);
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_block"), new BlockItem(RUBY_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "powerful_ruby_crystal"), POWERFUL_RUBY_CRYSTAL);
	
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_shovel"), new ShovelBase(new ToolMaterialRuby()));
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_pickaxe"), new PickaxeBase(new ToolMaterialRuby()));
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_sword"), new SwordBase(new ToolMaterialRuby()));
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_axe"), new AxeBase(new ToolMaterialRuby()));
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_hoe"), new HoeBase(new ToolMaterialRuby()));
	
	Registry.register(Registry.BLOCK, new Identifier("rubycrystal", "ruby_ore"), RUBY_ORE);
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_ore"), new BlockItem(RUBY_ORE, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
	
	RegistryKey<ConfiguredFeature<?, ?>> rubyOreOverworld = RegistryKey.of(Registry.CONFIGURED_FEATURE_WORLDGEN, new Identifier("rubycrystal", "ruby_ore"));
	Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, rubyOreOverworld.getValue(), RUBY_ORE_OVERWORLD);
	BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, rubyOreOverworld);

	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_helmet"), new BaseArmor(RUBY_ARMOR, EquipmentSlot.HEAD));
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_chestplate"), new BaseArmor(RUBY_ARMOR, EquipmentSlot.CHEST));
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_leggings"), new BaseArmor(RUBY_ARMOR, EquipmentSlot.LEGS));
	Registry.register(Registry.ITEM, new Identifier("rubycrystal", "ruby_boots"), new BaseArmor(RUBY_ARMOR, EquipmentSlot.FEET));
	}
}
